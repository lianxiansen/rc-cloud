package com.rc.cloud.app.operate.domain.category;

import com.rc.cloud.app.operate.domain.category.valobj.*;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 商品分类
 */
public class ProductCategoryEntity extends Entity {
    private ProductCategoryId id;
    private TenantId tenantId;
    private ProductCategoryIcon productCategoryIcon;
    private ProductCategoryPage productCategoryPage;
    private ProductCategoryParent productCategoryParent;
    private ProductCategoryLayer productCategoryLayer;
    private ProductCategoryEnable productCategoryEnable;
    private ProductCategorySort productCategorySort;
    private ProductCategoryDeleted productCategoryDeleted;
    public ProductCategoryEntity(ProductCategoryId id){
        this.id = id;
    }
}
