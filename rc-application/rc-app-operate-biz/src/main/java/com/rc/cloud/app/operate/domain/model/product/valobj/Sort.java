package com.rc.cloud.app.operate.domain.model.product.valobj;
import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Sort extends ValueObject{
    private int value;

    public Sort(int value){
        this.value=value;
    }
    public Sort(){
        setValue(0);
    }

    public void setValue(int value){
        AssertUtils.assertArgumentRange(value,0,100,"Sort is not in range(0,100)");
        this.value=value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    public int getValue() {
        return value;
    }
}
