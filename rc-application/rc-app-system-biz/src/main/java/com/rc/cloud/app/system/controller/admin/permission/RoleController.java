package com.rc.cloud.app.system.controller.admin.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rc.cloud.app.system.api.permission.entity.SysMenuDO;
import com.rc.cloud.app.system.api.permission.entity.SysRoleDO;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.convert.permission.MenuConvert;
import com.rc.cloud.app.system.convert.permission.RoleConvert;
import com.rc.cloud.app.system.convert.user.UserConvert;
import com.rc.cloud.app.system.mapper.permission.UserRoleMapper;
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

@Tag(name = "管理后台 - 角色")
@RestController
@RequestMapping("/sys/role")
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

    @PostMapping("/create")
    @Operation(summary = "创建角色")
    @PreAuthorize("@pms.hasPermission('sys:role:create')")
    public CodeResult<Long> createRole(@Valid @RequestBody RoleCreateReqVO reqVO) {
        return CodeResult.ok(roleService.createRole(reqVO, null));
    }

    @PutMapping("/update")
    @Operation(summary = "修改角色")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public CodeResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO reqVO) {
        roleService.updateRole(reqVO);
        return CodeResult.ok(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改角色状态")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public CodeResult<Boolean> updateRoleStatus(@Valid @RequestBody RoleUpdateStatusReqVO reqVO) {
        roleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return CodeResult.ok(true);
    }

    @DeleteMapping()
    @Operation(summary = "删除角色")
    @Parameter(name = "id", description = "角色编号", required = true, example = "[1024, 2048]")
    @PreAuthorize("@pms.hasPermission('sys:role:delete')")
    public CodeResult<Boolean> deleteRole(@RequestBody List<Long> idList) {
        roleService.deleteRoles(idList);
        return CodeResult.ok(true);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获得角色信息")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<RoleRespVO> getRole(@PathVariable("id") Long id) {
        SysRoleDO role = roleService.getRole(id);
        RoleRespVO roleRespVO = RoleConvert.INSTANCE.convert(role);
        roleRespVO.setMenuIds(permissionService.getRoleMenuIds(id));
        return CodeResult.ok(roleRespVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得角色分页")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<PageResult<RoleRespVO>> getRolePage(RolePageReqVO reqVO) {
        PageResult<SysRoleDO> pageResult = roleService.getRolePage(reqVO);
        List<RoleRespVO> roleList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(roleDO -> {
            RoleRespVO respVO = RoleConvert.INSTANCE.convert(roleDO);
            roleList.add(respVO);
        });
        return CodeResult.ok(new PageResult<>(roleList, pageResult.getTotal()));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取角色精简信息列表", description = "只包含被开启的角色，主要用于前端的下拉选项")
    public CodeResult<List<RoleSimpleRespVO>> getSimpleRoleList() {
        // 获得角色列表，只要开启状态的
        List<SysRoleDO> list = roleService.getRoleListByStatus(singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysRoleDO::getSort));
        return CodeResult.ok(RoleConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/user/page")
    @Operation(summary = "角色用户-分页")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public CodeResult<PageResult<UserRespVO>> userPage(@Valid RoleUserPageVO pageVO) {
        IPage<SysUserDO> pageResult = userService.roleUserPage(pageVO);
        List<UserRespVO> userList = new ArrayList<>(pageResult.getRecords().size());
        pageResult.getRecords().forEach(userDO -> {
            UserRespVO respVO = UserConvert.INSTANCE.convert(userDO);
            userList.add(respVO);
        });
        return CodeResult.ok(new PageResult<>(userList, pageResult.getTotal()));
    }

    @GetMapping("menu")
    @Operation(summary = "获取菜单")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<List<MenuRespVO>> menu() {
        Long userId = SecurityUtils.getUser().getId();
        List<MenuRespVO> list = new ArrayList<>();
        List<SysMenuDO> userMenuList = menuService.getMenuList();
        if (userMenuList != null && !userMenuList.isEmpty()) {
            list = MenuConvert.INSTANCE.convertList(userMenuList);
        }
        return CodeResult.ok(TreeUtil.build(list));
    }

    @PostMapping("user/{roleId}")
    @Operation(summary = "分配角色给用户列表")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CodeResult<String> userSave(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIds) {
        userRoleMapper.saveUserList(roleId, userIds);
        return CodeResult.ok();
    }

    @DeleteMapping("user/{roleId}")
    @Operation(summary = "删除角色用户")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CodeResult<String> userDelete(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIds) {
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
