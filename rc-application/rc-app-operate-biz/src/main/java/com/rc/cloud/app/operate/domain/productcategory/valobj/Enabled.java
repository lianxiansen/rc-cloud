package com.rc.cloud.app.operate.domain.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Enabled extends ValueObject {
    private boolean value;
    public Enabled(){
        this.value = false;
    }
    public Enabled(boolean flag){
        this.value = flag;
    }

    public boolean isTrue(){
        return value;
    }
    public boolean isFalse(){
        return !value;
    }

    public boolean value(){
        return value;
    }

}
