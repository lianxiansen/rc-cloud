package com.rc.cloud.app.operate.domain.model.product.identifier;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName:
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class BrandId extends ValueObject {
    private String value;
    public BrandId(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
