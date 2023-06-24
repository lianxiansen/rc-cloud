package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.AssertionConcern;
import com.rc.cloud.app.product.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductMasterImage extends ValueObject {
    private String value;
    public ProductMasterImage(String value){
        value=value;
    }
    public String getValue() {
        return value;
    }

}
