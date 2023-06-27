package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.category.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategory;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductCategoryConvert {
    public ProductCategoryConvert(){
    }

    public ProductCategoryAggregation convertToProductCategoryEntry(ProductCategory productCategory){
        return new ProductCategoryAggregation(new ProductCategoryId( productCategory.getId().toString()));
    }
}
