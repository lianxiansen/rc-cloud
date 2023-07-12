package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class OnshelfStatus implements ValueObject<OnshelfStatus> {
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

    @Override
    public boolean sameValueAs(OnshelfStatus other) {
        return false;
    }
}
