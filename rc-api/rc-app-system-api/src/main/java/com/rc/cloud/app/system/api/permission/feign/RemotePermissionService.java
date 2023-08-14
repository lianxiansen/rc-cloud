package com.rc.cloud.app.system.api.permission.feign;

import com.rc.cloud.app.system.api.permission.dto.DeptDataPermissionRespDTO;
import com.rc.cloud.app.system.api.permission.dto.PermissionGetReqDTO;
import com.rc.cloud.app.system.api.permission.dto.RoleGetReqDTO;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.feign.FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author WJF
 * @create 2023-08-14 9:13
 * @description 通过feign远程调用权限服务
 */
@FeignClient(
        contextId = "remoteUserService",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        configuration = FeignRequestInterceptor.class
)
public interface RemotePermissionService {
    /**
     * 获得拥有多个角色的用户编号集合
     *
     * @param roleIds 角色编号集合
     * @return 用户编号集合
     */
    @PostMapping("/sys/permission/getUserRoleIdListByRoleIds")
    CodeResult<Set<String>> getUserRoleIdListByRoleIds(@RequestBody List<String> roleIds);

    /**
     * 判断是否有权限，任一一个即可
     *
     * @return 是否
     */
    @PostMapping("/sys/permission/hasAnyPermissions")
    CodeResult<Boolean> hasAnyPermissions(@RequestBody PermissionGetReqDTO dto);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @return 是否
     */
    @PostMapping("/sys/permission/hasAnyRoles")
    CodeResult<Boolean> hasAnyRoles(@RequestBody RoleGetReqDTO dto);

    /**
     * 获得登陆用户的部门数据权限
     *
     * @param userId 用户编号
     * @return 部门数据权限
     */
    @PostMapping("/sys/permission/getDeptDataPermission")
    CodeResult<DeptDataPermissionRespDTO> getDeptDataPermission(@RequestBody String userId);

}
