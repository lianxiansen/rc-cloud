package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Layer extends AssertionConcern {
    private static final int MIN=1;
    private int value;
    public Layer(){
        this.value =MIN;
    }
    public Layer(int level){
        setValue(level);
    }
    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.assertArgumentRange(value,1,3,"the value of Layer is not in range(1,3)");
        this.value=value;
    }
}
