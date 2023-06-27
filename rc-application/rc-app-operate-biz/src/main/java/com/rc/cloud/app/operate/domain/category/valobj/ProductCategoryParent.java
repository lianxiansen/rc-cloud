package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductCategoryParent extends AssertionConcern {
    private String id;
    public ProductCategoryParent(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
}
