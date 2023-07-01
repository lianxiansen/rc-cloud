package com.rc.cloud.app.operate.domain.model.brand.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: Sort
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Sort extends ValueObject {
    private int value;
    public Sort(){
        setValue(0);
    }

    public void setValue(int value){
        this.assertArgumentRange(value,0,100,"Sort is not in range(0,100)");
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
