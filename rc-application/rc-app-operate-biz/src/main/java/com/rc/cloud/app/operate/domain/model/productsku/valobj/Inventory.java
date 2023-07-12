package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

public class Inventory implements ValueObject<Inventory> {

    public Inventory(int value){
        this.value=value;
    }
    private int value;

    public int getValue() {
        return value;
    }

    public Inventory setValue(int value) {
        return new Inventory(value);
    }

    @Override
    public boolean sameValueAs(Inventory other) {
        return false;
    }
}
