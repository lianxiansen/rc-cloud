package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;


/**
 * 装箱数
 */
public class PackingNumber extends ValueObject {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            PackingNumber typedObject = (PackingNumber) other;
        }
        return false;
    }
}
