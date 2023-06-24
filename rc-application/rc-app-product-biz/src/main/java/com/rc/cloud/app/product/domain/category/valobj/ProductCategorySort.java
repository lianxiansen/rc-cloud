package com.rc.cloud.app.product.domain.category.valobj;

import com.rc.cloud.app.product.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductCategorySort extends AssertionConcern {
    private int value;
    public ProductCategorySort(int value){
        this.value = value;
    }
}
