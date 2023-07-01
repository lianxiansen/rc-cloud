package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Remark extends ValueObject {
    private String value;
    public Remark(String value){
        this.setValue(value);
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        if(value.length()>1000){
            throw new IllegalArgumentException("");
        }

    }
}
