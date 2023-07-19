package com.rc.cloud.app.system.controller.admin.v1.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rc.cloud.app.system.convert.permission.MenuConvert;
import com.rc.cloud.app.system.convert.permission.RoleConvert;
import com.rc.cloud.app.system.convert.user.UserConvert;
import com.rc.cloud.app.system.mapper.permission.UserRoleMapper;
import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.permission.MenuService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.permission.menu.MenuRespVO;
import com.rc.cloud.app.system.vo.permission.role.*;
import com.rc.cloud.app.system.vo.user.user.UserRespVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.tree.TreeUtil;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.singleton;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色管理
 */
@Tag(name = "管理后台 - 角色")
@RestController
@RequestMapping("/admin/role")
@Validated
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private AdminUserService userService;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 创建角色
     *
     * @param reqVO 角色信息
     * @return 创建信息
     */
    @PostMapping("/create")
    @Operation(summary = "创建角色")
    @PreAuthorize("@pms.hasPermission('sys:role:create')")
    public CodeResult<String> createRole(@Valid @RequestBody RoleCreateReqVO reqVO) {
        return CodeResult.ok(roleService.createRole(reqVO, null));
    }

    /**
     * 修改角色
     *
     * @param reqVO 角色信息
     * @return 是否修改成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改角色")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public CodeResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO reqVO) {
        roleService.updateRole(reqVO);
        return CodeResult.ok(true);
    }

    /**
     * 修改角色状态
     *
     * @param reqVO 角色状态信息
     * @return 是否修改成功
     */
    @PutMapping("/update-status")
    @Operation(summary = "修改角色状态")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public CodeResult<Boolean> updateRoleStatus(@Valid @RequestBody RoleUpdateStatusReqVO reqVO) {
        roleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return CodeResult.ok(true);
    }

    /**
     * 删除角色
     *
     * @param idList 角色编号列表
     * @return 是否删除成功
     */
    @DeleteMapping()
    @Operation(summary = "删除角色")
    @Parameter(name = "id", description = "角色编号", required = true, example = "[1024, 2048]")
    @PreAuthorize("@pms.hasPermission('sys:role:delete')")
    public CodeResult<Boolean> deleteRole(@RequestBody List<String> idList) {
        roleService.deleteRoles(idList);
        return CodeResult.ok(true);
    }

    /**
     * 根据ID获得角色信息
     *
     * @param id 角色编号
     * @return 角色信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "获得角色信息")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<RoleRespVO> getRole(@PathVariable("id") String id) {
        SysRolePO role = roleService.getRole(id);
        RoleRespVO roleRespVO = RoleConvert.INSTANCE.convert(role);
        roleRespVO.setMenuIds(permissionService.getRoleMenuIds(id));
        return CodeResult.ok(roleRespVO);
    }

    /**
     * 获得角色分页
     *
     * @param reqVO 查询条件
     * @return 角色分页
     */
    @GetMapping("/page")
    @Operation(summary = "获得角色分页")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<PageResult<RoleRespVO>> getRolePage(RolePageReqVO reqVO) {
        PageResult<SysRolePO> pageResult = roleService.getRolePage(reqVO);
        List<RoleRespVO> roleList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(roleDO -> {
            RoleRespVO respVO = RoleConvert.INSTANCE.convert(roleDO);
            roleList.add(respVO);
        });
        return CodeResult.ok(new PageResult<>(roleList, pageResult.getTotal()));
    }

    /**
     * 获得角色列表
     *
     * @return 角色列表
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获取角色精简信息列表", description = "只包含被开启的角色，主要用于前端的下拉选项")
    public CodeResult<List<RoleSimpleRespVO>> getSimpleRoleList() {
        // 获得角色列表，只要开启状态的
        List<SysRolePO> list = roleService.getRoleListByStatus(singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysRolePO::getSort));
        return CodeResult.ok(RoleConvert.INSTANCE.convertList02(list));
    }

    /**
     * 获得角色用户分页
     *
     * @param pageVO 查询条件
     * @return 角色用户分页
     */
    @GetMapping("/user/page")
    @Operation(summary = "角色用户-分页")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<PageResult<UserRespVO>> userPage(@Valid RoleUserPageVO pageVO) {
        IPage<SysUserPO> pageResult = userService.roleUserPage(pageVO);
        List<UserRespVO> userList = new ArrayList<>(pageResult.getRecords().size());
        pageResult.getRecords().forEach(userDO -> {
            UserRespVO respVO = UserConvert.INSTANCE.convert(userDO);
            userList.add(respVO);
        });
        return CodeResult.ok(new PageResult<>(userList, pageResult.getTotal()));
    }

    /**
     * 获得角色菜单树
     *
     * @return 角色菜单树
     */
    @GetMapping("menu")
    @Operation(summary = "获取菜单")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<List<MenuRespVO>> menu() {
        String userId = SecurityUtils.getUser().getId();
        List<MenuRespVO> list = new ArrayList<>();
        List<SysMenuPO> userMenuList = menuService.getMenuList();
        if (userMenuList != null && !userMenuList.isEmpty()) {
            list = MenuConvert.INSTANCE.convertList(userMenuList);
        }
        return CodeResult.ok(TreeUtil.build(list));
    }

    /**
     * 分配角色给用户列表
     *
     * @param roleId  角色编号
     * @param userIds 用户编号列表
     * @return 是否分配成功
     */
    @PostMapping("user/{roleId}")
    @Operation(summary = "分配角色给用户列表")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CodeResult<String> userSave(@PathVariable("roleId") String roleId, @RequestBody List<String> userIds) {
        userRoleMapper.saveUserList(roleId, userIds);
        return CodeResult.ok();
    }

    /**
     * 删除角色用户
     *
     * @param roleId  角色编号
     * @param userIds 用户编号列表
     * @return 是否删除成功
     */
    @DeleteMapping("user/{roleId}")
    @Operation(summary = "删除角色用户")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CodeResult<String> userDelete(@PathVariable("roleId") String roleId, @RequestBody List<String> userIds) {
        userRoleMapper.deleteByUserIds(roleId, userIds);
        return CodeResult.ok();
    }

//    @GetMapping("/export")
////    @OperateLog(type = EXPORT)
//    @PreAuthorize("@pms.hasPermission('sys:role:export')")
//    public void export(HttpServletResponse response, @Validated RoleExportReqVO reqVO) throws IOException {
//        List<SysRoleDO> list = roleService.getRoleList(reqVO);
//        List<RoleExcelVO> data = RoleConvert.INSTANCE.convertList03(list);
//        // 输出
//        ExcelUtils.write(response, "角色数据.xls", "角色列表", RoleExcelVO.class, data);
//    }

}
