package com.rc.cloud.app.system.api.permission;

import com.rc.cloud.app.system.service.permission.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色 API 实现类
 */
@Service
public class RoleApiImpl implements RoleApi {

    @Resource
    private RoleService roleService;

    /**
     * 校验角色
     *
     * @param ids 角色 ID 列表
     */
    @Override
    public void validRoleList(Collection<String> ids) {
        roleService.validateRoleList(ids);
    }
}
