package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.ValueObject;

/**
 * @ClassName:
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductFirstCategory extends ValueObject {
    private String value;
    public ProductFirstCategory(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
