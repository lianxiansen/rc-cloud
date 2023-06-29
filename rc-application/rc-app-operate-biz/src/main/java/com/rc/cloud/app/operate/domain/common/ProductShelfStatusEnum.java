package com.rc.cloud.app.operate.domain.common;

import com.rc.cloud.common.core.domain.ValueObject;

public enum ProductShelfStatusEnum implements ValueObject<ProductShelfStatusEnum> {

    InitShelf(0, "未上架"),
    OnShelf(1, "上架"),
    OffShelf(2, "已下架");

    public final Integer value;
    public final String name;

    ProductShelfStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public boolean sameValueAs(ProductShelfStatusEnum other) {
        return false;
    }
}
