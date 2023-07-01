package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: Sort
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Sort extends ValueObject {
    private int value;
    public Sort(){
        this(0);
    }
    public Sort(int value){
        setValue(value);
    }

    private void setValue(int value){
        validate(value);
        this.value=value;

    }

    private void validate(int value){
        this.assertArgumentRange(value,0,100,"Sort is not in range(0,100)");
    }

    public int getValue(){
        return value;
    }
}
