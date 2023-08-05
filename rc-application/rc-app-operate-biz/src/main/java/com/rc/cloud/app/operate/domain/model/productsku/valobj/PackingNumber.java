package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;


/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class PackingNumber extends ValueObject {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        AssertUtils.assertArgumentRange(value,0,99999999,"PackingNumber is not in range(0,99999999)");

        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            PackingNumber typedObject = (PackingNumber) other;
        }
        return false;
    }

    public PackingNumber(int value) {
        this.value = value;
    }
}
