package com.rc.cloud.app.product.domain.tenant.service;

import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;

/**
 * @ClassName: TenantRepository
 * @Author: liandy
 * @Date: 2023/6/24 09:22
 * @Description: TODO
 */
public interface TenantService {
    boolean exists(TenantId tenantId);
}
