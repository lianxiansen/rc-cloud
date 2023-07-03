package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

public class CategoryName  extends ValueObject {
    private String value;

    public CategoryName(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("商品名称不为空");
        }
        this.value=value;
    }
}