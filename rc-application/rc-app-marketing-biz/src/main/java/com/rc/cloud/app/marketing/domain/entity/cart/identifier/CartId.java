package com.rc.cloud.app.marketing.domain.entity.cart.identifier;

import com.rc.cloud.common.core.domain.AbstractId;

/**
 * @author WJF
 * @create 2023-07-22 9:28
 * @description TODO
 */

public class CartId extends AbstractId {

    public CartId(String id) {
        super(id);
    }

    public String getId(){
        return super.id();
    }

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
