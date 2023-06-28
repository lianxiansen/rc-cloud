package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: Sort
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Sort extends AssertionConcern {
    private int value;
    public Sort(){
        setValue(0);
    }

    public void setValue(int value){
        this.assertArgumentRange(value,0,100,"Sort is not in range(0,100)");
        this.value=value;
    }
}
