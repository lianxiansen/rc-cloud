package com.rc.cloud.app.system.controller.remote;

import com.rc.cloud.app.system.api.permission.dto.DeptDataPermissionRespDTO;
import com.rc.cloud.app.system.api.permission.dto.PermissionGetReqDTO;
import com.rc.cloud.app.system.api.permission.dto.RoleGetReqDTO;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author WJF
 * @create 2023-08-14 9:06
 * @description TODO
 */
@RestController
@RequestMapping("/sys/permission")
@Validated
public class RemotePermissionServerImpl {

    @Resource
    private PermissionService permissionService;

    @Inner
    @PostMapping("/getUserRoleIdListByRoleIds")
    public CodeResult<Set<String>> getUserRoleIdListByRoleIds(@RequestBody List<String> roleIds) {
        return CodeResult.ok(permissionService.getUserRoleIdListByRoleIds(roleIds));
    }

    @Inner
    @PostMapping("/hasAnyPermissions")
    public CodeResult<Boolean> hasAnyPermissions(@RequestBody PermissionGetReqDTO dto) {
        return CodeResult.ok(permissionService.hasAnyPermissions(dto.getUserId(), dto.getPermissions().toArray(new String[dto.getPermissions().size()])));
    }

    @Inner
    @PostMapping("/hasAnyRoles")
    public CodeResult<Boolean> hasAnyRoles(@RequestBody RoleGetReqDTO dto) {
        return CodeResult.ok(permissionService.hasAnyRoles(dto.getUserId(), dto.getRoles().toArray(new String[dto.getRoles().size()])));
    }

    @Inner
    @PostMapping("/getDeptDataPermission")
    public CodeResult<DeptDataPermissionRespDTO> getDeptDataPermission(@RequestBody String userId) {
        return CodeResult.ok(permissionService.getDeptDataPermission(userId));
    }
}
