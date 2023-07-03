package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

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

    public void setValue(int value){
        this.value = value;
    }

}
