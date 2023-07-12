package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

public class LimitBuy implements ValueObject<LimitBuy> {
    @Override
    public boolean sameValueAs(LimitBuy other) {
        return false;
    }
}
