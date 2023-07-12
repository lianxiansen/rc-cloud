package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

public class Price implements ValueObject<Price> {


    @Override
    public boolean sameValueAs(Price other) {
        return false;
    }
}
