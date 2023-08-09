package com.rc.cloud.app.operate.domain.model.product.event;

import com.rc.cloud.common.core.domain.DomainEvent;

import java.util.Date;

/**
 * @ClassName: ProductCreatedEvent
 * @Author: liandy
 * @Date: 2023/6/24 15:54
 * @Description: TODO
 */
public class ProductCreatedEvent implements DomainEvent {
    private int eventVersion;
    private String exclusiveOwner;
    private Date occurredOn;

    public ProductCreatedEvent(
            String anExclusiveOwner) {

        super();

        this.eventVersion = 1;
        this.exclusiveOwner = anExclusiveOwner;
        this.occurredOn = new Date();
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
