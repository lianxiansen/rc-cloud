package com.rc.cloud.app.operate.domain.tenant.valobj;

import com.rc.cloud.app.operate.domain.common.AbstractId;

/**
 * @ClassName: ProductCategoryEntryId
 * @Author: liandy
 * @Date: 2023/6/23 13:15
 * @Description: TODO
 */
public class TenantId extends AbstractId {
    public TenantId(String id) {
        super(id);
    }

    protected TenantId() {
        super();
    }

}