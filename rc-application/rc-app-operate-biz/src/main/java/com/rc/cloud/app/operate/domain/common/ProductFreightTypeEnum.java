package com.rc.cloud.app.operate.domain.common;

import com.rc.cloud.common.core.domain.ValueObject;

public enum ProductFreightTypeEnum  implements ValueObject<ProductFreightTypeEnum> {


    Fixed(0, "固定"),
    Template(1,"运费模板");

    public final Integer value;
    public final String name;

    ProductFreightTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }


    @Override
    public boolean sameValueAs(ProductFreightTypeEnum other) {
        return false;
    }
}
