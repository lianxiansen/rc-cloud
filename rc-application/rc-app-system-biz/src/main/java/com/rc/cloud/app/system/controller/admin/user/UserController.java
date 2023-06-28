package com.rc.cloud.app.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rc.cloud.app.system.api.dept.entity.SysDeptDO;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.convert.user.UserConvert;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.user.user.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import com.rc.cloud.common.security.utils.MsgUtils;
import com.rc.cloud.common.security.utils.SecurityUtils;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.rc.cloud.common.core.util.collection.CollectionUtils.convertList;
import static com.rc.cloud.common.core.util.collection.CollectionUtils.convertSet;


@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/sys/user")
@Validated
public class UserController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @Operation(summary = "新增用户")
    @PreAuthorize("@pms.hasPermission('sys:user:create')")
    public CodeResult<Long> createUser(@Valid @RequestBody UserCreateReqVO reqVO) {
        Long id = userService.createUser(reqVO);
        return CodeResult.ok(id);
    }

    @PutMapping("update")
    @Operation(summary = "修改用户")
    @PreAuthorize("@pms.hasPermission('sys:user:update')")
    public CodeResult<Boolean> updateUser(@Valid @RequestBody UserUpdateReqVO reqVO) {
        userService.updateUser(reqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping()
    @Operation(summary = "删除用户")
    @Parameter(name = "idList", description = "编号列表", required = true, example = "[1024,1025]")
    @PreAuthorize("@pms.hasPermission('sys:user:delete')")
    public CodeResult<Boolean> deleteUser(@RequestBody List<Long> idList) {
        Long userId = SecurityUtils.getUser().getId();
        if (idList.contains(userId)) {
            return CodeResult.fail("不能删除当前登录用户");
        }
        userService.deleteUsers(idList);
        return CodeResult.ok(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "重置用户密码")
    @PreAuthorize("@pms.hasPermission('sys:user:update-password')")
    public CodeResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(reqVO.getId(), reqVO.getPassword());
        return CodeResult.ok(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改用户状态")
    @PreAuthorize("@pms.hasPermission('sys:user:update')")
    public CodeResult<Boolean> updateUserStatus(@Valid @RequestBody UserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return CodeResult.ok(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户分页列表")
    @PreAuthorize("@pms.hasPermission('sys:user:query')")
    public CodeResult<PageResult<UserPageItemRespVO>> getUserPage(@Valid UserPageReqVO reqVO) {
        // 获得用户分页列表
        PageResult<SysUserDO> pageResult = userService.getUserPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return CodeResult.ok(new PageResult<>(pageResult.getTotal())); // 返回空
        }

        // 获得拼接需要的数据
        Collection<Long> deptIds = convertList(pageResult.getList(), SysUserDO::getDeptId);
        Map<Long, SysDeptDO> deptMap = deptService.getDeptMap(deptIds);
        // 拼接结果返回
        List<UserPageItemRespVO> userList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(user -> {
            UserPageItemRespVO respVO = UserConvert.INSTANCE.convert(user);
            respVO.setDept(UserConvert.INSTANCE.convert(deptMap.get(user.getDeptId())));
            userList.add(respVO);
        });
        return CodeResult.ok(new PageResult<>(userList, pageResult.getTotal()));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取用户精简信息列表", description = "只包含被开启的用户，主要用于前端的下拉选项")
    public CodeResult<List<UserSimpleRespVO>> getSimpleUserList() {
        // 获用户列表，只要开启状态的
        List<SysUserDO> list = userService.getUserListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 排序后，返回给前端
        return CodeResult.ok(UserConvert.INSTANCE.convertList04(list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获得用户详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('system:user:query')")
    public CodeResult<UserRespVO> getUser(@PathVariable("id") Long id) {
        SysUserDO user = userService.getUser(id);
        // 获得部门数据
        SysDeptDO dept = deptService.getDept(user.getDeptId());
        UserPageItemRespVO userPageItemRespVO = UserConvert.INSTANCE.convert(user);
        userPageItemRespVO.setDept(UserConvert.INSTANCE.convert(dept));
        return CodeResult.ok(userPageItemRespVO);
    }

    /**
     * 获取指定用户全部信息
     * @return 用户信息
     */
    @Inner
    @GetMapping("/info/{username}")
    public CodeResult<UserInfo> info(@PathVariable String username) {
        // 硬编码设置租户ID
        TenantContextHolder.setTenantId(1L);
        SysUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        UserInfo userInfo = userService.getUserInfo(user);
        return CodeResult.ok(userInfo);
    }

//    @GetMapping("/export")
//    @Operation(summary = "导出用户")
////    @PreAuthorize("@ss.hasPermission('system:user:export')")
////    @OperateLog(type = EXPORT)
//    public void exportUserList(@Validated UserExportReqVO reqVO,
//                               HttpServletResponse response) throws IOException {
//        // 获得用户列表
//        List<AdminUserDO> users = userService.getUserList(reqVO);
//
//        // 获得拼接需要的数据
//        Collection<Long> deptIds = convertList(users, AdminUserDO::getDeptId);
//        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
//        Map<Long, AdminUserDO> deptLeaderUserMap = userService.getUserMap(
//                convertSet(deptMap.values(), DeptDO::getLeaderUserId));
//        // 拼接数据
//        List<UserExcelVO> excelUsers = new ArrayList<>(users.size());
//        users.forEach(user -> {
//            UserExcelVO excelVO = UserConvert.INSTANCE.convert02(user);
//            // 设置部门
//            MapUtils.findAndThen(deptMap, user.getDeptId(), dept -> {
//                excelVO.setDeptName(dept.getName());
//                // 设置部门负责人的名字
//                MapUtils.findAndThen(deptLeaderUserMap, dept.getLeaderUserId(),
//                        deptLeaderUser -> excelVO.setDeptLeaderNickname(deptLeaderUser.getNickname()));
//            });
//            excelUsers.add(excelVO);
//        });
//
//        // 输出
//        ExcelUtils.write(response, "用户数据.xls", "用户列表", UserExcelVO.class, excelUsers);
//    }
//
//    @GetMapping("/get-import-template")
//    @Operation(summary = "获得导入用户模板")
//    public void importTemplate(HttpServletResponse response) throws IOException {
//        // 手动创建导出 demo
//        List<UserImportExcelVO> list = Arrays.asList(
//                UserImportExcelVO.builder().username("yunai").deptId(1L).email("yunai@iocoder.cn").mobile("15601691300")
//                        .nickname("芋道").status(CommonStatusEnum.ENABLE.getStatus()).sex(SexEnum.MALE.getSex()).build(),
//                UserImportExcelVO.builder().username("yuanma").deptId(2L).email("yuanma@iocoder.cn").mobile("15601701300")
//                        .nickname("源码").status(CommonStatusEnum.DISABLE.getStatus()).sex(SexEnum.FEMALE.getSex()).build()
//        );
//
//        // 输出
//        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", UserImportExcelVO.class, list);
//    }

//    @PostMapping("/import")
//    @Operation(summary = "导入用户")
//    @Parameters({
//            @Parameter(name = "file", description = "Excel 文件", required = true),
//            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
//    })
//    @PreAuthorize("@ss.hasPermission('system:user:import')")
//    public CodeResult<UserImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
//                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
//        List<UserImportExcelVO> list = ExcelUtils.read(file, UserImportExcelVO.class);
//        return CodeResult.ok(userService.importUserList(list, updateSupport));
//    }

}
