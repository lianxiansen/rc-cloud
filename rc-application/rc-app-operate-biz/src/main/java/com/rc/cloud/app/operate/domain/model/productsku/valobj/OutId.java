package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class OutId extends ValueObject{
    private String value;
    public OutId(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Inventory typedObject = (Inventory) other;
        }
        return false;
    }
}
