package com.rc.cloud.app.system.biz.controller.admin.permission;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.biz.service.permission.PermissionService;
import com.rc.cloud.app.system.biz.service.tenant.TenantService;
import com.rc.cloud.app.system.biz.vo.permission.permission.PermissionAssignRoleDataScopeReqVO;
import com.rc.cloud.app.system.biz.vo.permission.permission.PermissionAssignRoleMenuReqVO;
import com.rc.cloud.app.system.biz.vo.permission.permission.PermissionAssignUserRoleReqVO;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;


/**
 * 权限 Controller，提供赋予用户、角色的权限的 API 接口
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 权限")
@RestController
@RequestMapping("/sys/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private TenantService tenantService;

    @Operation(summary = "获得角色拥有的菜单编号")
    @Parameter(name = "roleId", description = "角色编号", required = true)
    @GetMapping("/list-role-resources")
//    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CodeResult<Set<Long>> listRoleMenus(Long roleId) {
        return CodeResult.ok(permissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/assign-role-menu")
    @Operation(summary = "赋予角色菜单")
//    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CodeResult<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        // 开启多租户的情况下，需要过滤掉未开通的菜单
        tenantService.handleTenantMenu(menuIds -> reqVO.getMenuIds().removeIf(menuId -> !CollUtil.contains(menuIds, menuId)));

        // 执行菜单的分配
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return CodeResult.ok(true);
    }

    @PostMapping("/assign-role-data-scope")
    @Operation(summary = "赋予角色数据权限")
//    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-data-scope')")
    public CodeResult<Boolean> assignRoleDataScope(@Valid @RequestBody PermissionAssignRoleDataScopeReqVO reqVO) {
        permissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return CodeResult.ok(true);
    }

    @Operation(summary = "获得管理员拥有的角色编号列表")
    @Parameter(name = "userId", description = "用户编号", required = true)
    @GetMapping("/list-user-roles")
//    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CodeResult<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return CodeResult.ok(permissionService.getUserRoleIdListByUserId(userId));
    }

    @Operation(summary = "赋予用户角色")
    @PostMapping("/assign-user-role")
//    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CodeResult<Boolean> assignUserRole(@Validated @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return CodeResult.ok(true);
    }

}
