package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class OnshelfStatus extends ValueObject{
    private int value;
    public OnshelfStatus(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
