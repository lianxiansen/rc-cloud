package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class TotalInventory extends ValueObject{


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
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            TotalInventory typedObject = (TotalInventory) other;
        }
        return false;
    }
}
