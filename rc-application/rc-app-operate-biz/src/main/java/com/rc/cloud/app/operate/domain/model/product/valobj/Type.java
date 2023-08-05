package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Type extends ValueObject {
    private int value;
    public Type(int value){
        this.setValue(value);
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        AssertUtils.assertArgumentNotNull(value,"value must not be null");
        if(value <0){
            throw new IllegalArgumentException("商品类型错误");
        }
        this.value=value;
    }


    @Override
    public boolean equals(Object other) {
        return false;
    }
}
