package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName:
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class SecondCategory extends ValueObject {
    private String value;
    public SecondCategory(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
