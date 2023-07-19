package com.rc.cloud.app.system.controller.admin.v1.permission;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleDataScopeReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleMenuReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignUserRoleReqVO;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 权限 Controller，提供赋予用户、角色的权限的 API 接口
 */
@Tag(name = "管理后台 - 权限")
@RestController
@RequestMapping("/admin/v1/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private TenantService tenantService;

    /**
     * 获得角色拥有的菜单编号
     *
     * @param roleId 角色编号
     * @return 菜单编号集合
     */
    @Operation(summary = "获得角色拥有的菜单编号")
    @Parameter(name = "roleId", description = "角色编号", required = true)
    @GetMapping("/list-role-resources/{roleId}")
    @PreAuthorize("@pms.hasPermission('sys:permission:assign-role-menu')")
    public CodeResult<Set<String>> listRoleMenus(@PathVariable String roleId) {
        return CodeResult.ok(permissionService.getRoleMenuIds(roleId));
    }

    /**
     * 赋予角色菜单
     *
     * @param reqVO 赋予角色菜单请求
     * @return 是否成功
     */
    @PostMapping("/assign-role-menu")
    @Operation(summary = "赋予角色菜单")
    @PreAuthorize("@pms.hasPermission('sys:permission:assign-role-menu')")
    public CodeResult<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        // 开启多租户的情况下，需要过滤掉未开通的菜单
        tenantService.handleTenantMenu(menuIds -> reqVO.getMenuIds().removeIf(menuId -> !CollUtil.contains(menuIds, menuId)));

        // 执行菜单的分配
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return CodeResult.ok(true);
    }

    /**
     * 赋予角色数据权限
     *
     * @param reqVO 赋予角色数据权限请求
     * @return 是否成功
     */
    @PostMapping("/assign-role-data-scope")
    @Operation(summary = "赋予角色数据权限")
    @PreAuthorize("@pms.hasPermission('sys:permission:assign-role-data-scope')")
    public CodeResult<Boolean> assignRoleDataScope(@Valid @RequestBody PermissionAssignRoleDataScopeReqVO reqVO) {
        permissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return CodeResult.ok(true);
    }

    /**
     * 获得管理员拥有的角色编号列表
     *
     * @param userId 用户编号
     * @return 角色编号列表
     */
    @Operation(summary = "获得管理员拥有的角色编号列表")
    @Parameter(name = "userId", description = "用户编号", required = true)
    @GetMapping("/list-user-roles/{userId}")
    @PreAuthorize("@pms.hasPermission('sys:permission:assign-user-role')")
    public CodeResult<Set<String>> listAdminRoles(@PathVariable("userId") String userId) {
        return CodeResult.ok(permissionService.getUserRoleIdListByUserId(userId));
    }

    /**
     * 赋予用户角色
     *
     * @param reqVO 赋予用户角色请求
     * @return 是否成功
     */
    @Operation(summary = "赋予用户角色")
    @PostMapping("/assign-user-role")
    @PreAuthorize("@pms.hasPermission('sys:permission:assign-user-role')")
    public CodeResult<Boolean> assignUserRole(@Validated @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return CodeResult.ok(true);
    }
}
