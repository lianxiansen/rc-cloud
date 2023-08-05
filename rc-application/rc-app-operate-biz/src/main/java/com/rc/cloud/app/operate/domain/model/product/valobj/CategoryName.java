package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class CategoryName  extends ValueObject {
    private String value;

    public CategoryName(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value=value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
