package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description: 分类名
 */
public class EnName extends ValueObject {
    /**
     * 分类名（英文名）
     */
    private String value;

    public EnName(String enName){
        setValue(enName);
    }

    private void setValue(String value){
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            EnName typedObject = (EnName) other;
            return this.value.equals(typedObject.value);
        }
        return false;
    }
}
