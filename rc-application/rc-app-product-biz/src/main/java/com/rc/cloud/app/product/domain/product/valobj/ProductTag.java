package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductTag extends AssertionConcern {
    private String value;
    public ProductTag(String value){
        value=value;
    }
    public String getValue() {
        return value;
    }

}
