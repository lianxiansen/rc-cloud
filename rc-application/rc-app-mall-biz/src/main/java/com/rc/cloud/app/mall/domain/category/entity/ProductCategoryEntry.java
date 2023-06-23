package com.rc.cloud.app.mall.domain.category.entity;

import com.rc.cloud.app.mall.domain.category.valobj.*;
import com.rc.cloud.app.mall.domain.common.Entity;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: TODO
 */
public class ProductCategoryEntry extends Entity {
    private ProductCategoryId id;
    private ProductCategoryTenant tenant;
    private ProductCategoryIcon productCategoryIcon;
    private ProductCategoryPage productCategoryPage;
    private ProductCategoryParent productCategoryParent;
    private ProductCategoryLayer productCategoryLayer;
    private ProductCategoryEnable productCategoryEnable;
    private ProductCategoryDeleted productCategoryDeleted;
    public ProductCategoryEntry(ProductCategoryId id){
        this.id = id;
    }
}
