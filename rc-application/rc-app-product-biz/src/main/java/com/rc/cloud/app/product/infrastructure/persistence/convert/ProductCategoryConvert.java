package com.rc.cloud.app.product.infrastructure.persistence.convert;

import com.rc.cloud.app.product.domain.category.ProductCategoryEntity;
import com.rc.cloud.app.product.domain.category.valobj.ProductCategoryId;
import com.rc.cloud.app.product.infrastructure.persistence.po.ProductCategory;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductCategoryConvert {
    public ProductCategoryConvert(){
    }

    public ProductCategoryEntity convertToProductCategoryEntry(ProductCategory productCategory){
        return new ProductCategoryEntity(new ProductCategoryId( productCategory.getId().toString()));
    }
}
