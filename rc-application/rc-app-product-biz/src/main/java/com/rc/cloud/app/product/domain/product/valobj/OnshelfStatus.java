package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class OnshelfStatus extends ValueObject {
    private int value;
    public OnshelfStatus(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}
