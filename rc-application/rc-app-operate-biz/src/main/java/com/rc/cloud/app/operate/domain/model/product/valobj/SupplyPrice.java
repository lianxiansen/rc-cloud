package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

public class SupplyPrice implements ValueObject<SupplyPrice> {
    @Override
    public boolean sameValueAs(SupplyPrice other) {
        return false;
    }
}
