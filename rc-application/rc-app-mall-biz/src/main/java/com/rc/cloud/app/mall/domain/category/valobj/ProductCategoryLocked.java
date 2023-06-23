package com.rc.cloud.app.mall.domain.category.valobj;

import com.rc.cloud.app.mall.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductCategoryLocked extends AssertionConcern {
    private boolean flag;
    public ProductCategoryLocked(boolean flag){
        this.flag=flag;
    }
    public boolean getFlag()
    {
        return flag;
    }
}
