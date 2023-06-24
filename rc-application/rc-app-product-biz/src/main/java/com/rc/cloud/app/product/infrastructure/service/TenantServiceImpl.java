package com.rc.cloud.app.product.infrastructure.service;

import com.rc.cloud.app.product.domain.tenant.service.TenantService;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TenantServiceImpl
 * @Author: liandy
 * @Date: 2023/6/24 14:59
 * @Description: TODO
 */
@Service
public class TenantServiceImpl implements TenantService {
    @Override
    public boolean exists(TenantId tenantId) {
        return true;
    }
}
