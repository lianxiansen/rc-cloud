package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

public class TotalInventory extends ValueObject {


    public TotalInventory(int value) {
        this.value = value;
    }

    private  int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
