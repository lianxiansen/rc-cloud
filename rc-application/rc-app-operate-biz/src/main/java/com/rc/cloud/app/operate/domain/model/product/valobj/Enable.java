package com.rc.cloud.app.operate.domain.model.product.valobj;


import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Enable implements ValueObject<Enable> {
    private boolean value;
    public Enable(){
        this.value = true;
    }
    public Enable(boolean flag){
        this.value = flag;
    }

    public boolean result(){
        return value;
    }

    @Override
    public boolean sameValueAs(Enable other) {
        return false;
    }
}
