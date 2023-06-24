package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductEnable extends ValueObject {
    private boolean value;
    public ProductEnable(){
        this.value = true;
    }
    public ProductEnable(boolean flag){
        this.value = flag;
    }

    public boolean result(){
        return value;
    }

}
