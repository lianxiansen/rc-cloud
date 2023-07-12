package com.rc.cloud.app.operate.domain.model.tenant;

import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.Entity;

/**
 * @ClassName: Tenant
 * @Author: liandy
 * @Date: 2023/6/24 09:04
 * @Description: TODO
 */
public class TenantEntity extends Entity {
    private TenantId id;

    @Override
    public TenantId getId() {
        return id;
    }
}
