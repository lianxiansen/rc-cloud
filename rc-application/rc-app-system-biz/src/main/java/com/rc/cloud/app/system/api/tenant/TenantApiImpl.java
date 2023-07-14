package com.rc.cloud.app.system.api.tenant;

import com.rc.cloud.app.system.service.tenant.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 多租户的 API 实现类
 */
@Service
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    /**
     * 获取租户ID列表
     */
    @Override
    public List<String> getTenantIdList() {
        return tenantService.getTenantIdList();
    }

    /**
     * 校验租户
     *
     * @param id 租户ID
     */
    @Override
    public void validateTenant(String id) {
        tenantService.validTenant(id);
    }
}
