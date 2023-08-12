package com.rc.cloud.app.distributor.infrastructure.config.datapermission;

import com.rc.cloud.app.system.api.permission.PermissionApi;
import com.rc.cloud.app.system.api.permission.dto.DeptDataPermissionRespDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * 权限 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PermissionApiImpl implements PermissionApi {


    @Override
    public Set<String> getUserRoleIdListByRoleIds(Collection<String> roleIds) {
        return null;
    }

    @Override
    public boolean hasAnyPermissions(String userId, String... permissions) {
        return true;
    }

    @Override
    public boolean hasAnyRoles(String userId, String... roles) {
        return true;
    }

    @Override
    public DeptDataPermissionRespDTO getDeptDataPermission(String userId) {
        return null;
    }

}
