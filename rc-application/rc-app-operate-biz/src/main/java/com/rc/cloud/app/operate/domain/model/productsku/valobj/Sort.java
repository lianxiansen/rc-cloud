package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: Sort
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Sort extends ValueObject {
    private int value;
    public Sort(int value){
        this.value= value;
    }

    public void setValue(int value){
        this.assertArgumentRange(value,0,100,"Sort is not in range(0,100)");
        this.value=value;
    }
}
