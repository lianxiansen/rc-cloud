package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description: 分类名
 */
public class ChName extends ValueObject{
    /**
     * 分类名（中文名）
     */
    private String value;
    public ChName(String value){
        setValue(value);
    }

    private void setValue(String value){
        AssertUtils.assertArgumentNotEmpty(value,"chName must not be empty");
        this.value = value;
    }

    public String value() {
        return this.value;
    }



    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            ChName typedObject = (ChName) other;
            return this.value.equals(typedObject.value);
        }
        return false;
    }
}
