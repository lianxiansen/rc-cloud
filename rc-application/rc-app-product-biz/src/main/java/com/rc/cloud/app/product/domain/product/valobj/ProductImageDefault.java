package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductImageDefault extends AssertionConcern {
    private int value;
    public ProductImageDefault(int value){
        this.value=value;
    }
    public ProductImageDefault(){
        this.value=0;
    }
}
