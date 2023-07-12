package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Origin implements ValueObject<Origin> {
    private int value;
    public Origin(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    @Override
    public boolean sameValueAs(Origin other) {
        return false;
    }
}
