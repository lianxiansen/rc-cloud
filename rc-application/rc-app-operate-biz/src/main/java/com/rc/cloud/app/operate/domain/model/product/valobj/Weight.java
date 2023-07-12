package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

public class Weight implements ValueObject<Recommend> {
    @Override
    public boolean sameValueAs(Recommend other) {
        return false;
    }
}
