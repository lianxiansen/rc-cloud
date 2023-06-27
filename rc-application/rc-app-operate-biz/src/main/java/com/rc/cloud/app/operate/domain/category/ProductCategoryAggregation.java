package com.rc.cloud.app.operate.domain.category;

import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.category.valobj.*;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 商品分类
 */
public class ProductCategoryAggregation extends Entity {
    private ProductCategoryId id;
    private TenantId tenantId;
    private Icon icon;
    private Page page;
    private Parent parent;
    private Layer layer;
    private Enable enable;
    private Sort sort;
    public ProductCategoryAggregation(ProductCategoryId id){
        this.id = id;
    }
}
