package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductOnshelfStatus extends ValueObject {
    private int value;
    public ProductOnshelfStatus(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}