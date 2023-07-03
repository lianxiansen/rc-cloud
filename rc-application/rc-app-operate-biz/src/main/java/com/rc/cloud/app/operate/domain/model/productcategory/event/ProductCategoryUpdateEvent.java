package com.rc.cloud.app.operate.domain.model.productcategory.event;

import com.rc.cloud.app.operate.domain.common.DomainEvent;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.Date;

/**
 * @ClassName: ProductCategoryUpdateEvent
 * @Author: liandy
 * @Date: 2023/6/24 15:54
 * @Description: TODO
 */
public class ProductCategoryUpdateEvent implements DomainEvent {
    private int eventVersion;
    private String exclusiveOwner;
    private Date occurredOn;
    private TenantId tenant;

    public ProductCategoryUpdateEvent(
            TenantId tenantId,
            String anExclusiveOwner) {

        super();

        this.eventVersion = 1;
        this.exclusiveOwner = anExclusiveOwner;
        this.occurredOn = new Date();
        this.tenant = tenantId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String exclusiveOwner() {
        return this.exclusiveOwner;
    }



    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }


}
