package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Origin extends ValueObject{
    private int value;
    public Origin(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
