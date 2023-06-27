package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductCategoryLayer extends AssertionConcern {
    private int level;
    public ProductCategoryLayer(int level){
        this.level=level;
    }
    public int getLevel(){
        return level;
    }
}
