package com.rc.cloud.app.product.domain.category;

import com.rc.cloud.app.product.domain.category.valobj.*;
import com.rc.cloud.app.product.domain.common.Entity;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 商品分类
 */
public class ProductCategoryEntry extends Entity {
    private ProductCategoryId id;
    private TenantId tenantId;
    private ProductCategoryIcon productCategoryIcon;
    private ProductCategoryPage productCategoryPage;
    private ProductCategoryParent productCategoryParent;
    private ProductCategoryLayer productCategoryLayer;
    private ProductCategoryEnable productCategoryEnable;
    private ProductCategorySort productCategorySort;
    private ProductCategoryDeleted productCategoryDeleted;
    public ProductCategoryEntry(ProductCategoryId id){
        this.id = id;
    }
}
