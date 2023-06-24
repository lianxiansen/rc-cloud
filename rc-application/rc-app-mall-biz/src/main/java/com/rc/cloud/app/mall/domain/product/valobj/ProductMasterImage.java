package com.rc.cloud.app.mall.domain.product.valobj;

import com.rc.cloud.app.mall.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductMasterImage extends AssertionConcern {
    private String value;
    public ProductMasterImage(String value){
        value=value;
    }
    public String getValue() {
        return value;
    }

}
