package com.rc.cloud.app.system.api.tenant;

import com.rc.cloud.app.system.service.tenant.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多租户的 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    @Override
    public List<String> getTenantIdList() {
        return tenantService.getTenantIdList();
    }

    @Override
    public void validateTenant(String id) {
        tenantService.validTenant(id);
    }
}
