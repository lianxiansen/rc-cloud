package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Type extends ValueObject {
    private Integer value;
    public Type(Integer value){
        this.setValue(value);
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        AssertUtils.assertArgumentNotNull(value,"value must not be null");
        if(value.intValue() <0){
            throw new IllegalArgumentException("商品类型错误");
        }

    }


    @Override
    public boolean equals(Object other) {
        return false;
    }
}
