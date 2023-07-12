package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

public class TotalInventory implements ValueObject<TotalInventory> {


    public TotalInventory(int value){
        this.value=value;
    }
    private int value;

    public int getValue() {
        return value;
    }

    public TotalInventory setValue(int value) {
        return new TotalInventory(value);
    }

    @Override
    public boolean sameValueAs(TotalInventory other) {
        return false;
    }
}
