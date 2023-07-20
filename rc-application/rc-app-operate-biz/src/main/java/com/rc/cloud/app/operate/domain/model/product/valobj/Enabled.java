package com.rc.cloud.app.operate.domain.model.product.valobj;


import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Enabled extends ValueObject{
    private boolean value;
    public Enabled(){
        this.value = true;
    }
    public Enabled(boolean flag){
        this.value = flag;
    }

    public boolean result(){
        return value;
    }


    @Override
    public boolean equals(Object other) {
        return false;
    }
}
