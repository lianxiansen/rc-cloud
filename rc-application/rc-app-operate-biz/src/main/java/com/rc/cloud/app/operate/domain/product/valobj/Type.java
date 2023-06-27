package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

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
        if (value == null) {
            throw new IllegalArgumentException("商品类型错误");
        }
        if(value.intValue() <0){
            throw new IllegalArgumentException("商品类型错误");
        }
    }
}
