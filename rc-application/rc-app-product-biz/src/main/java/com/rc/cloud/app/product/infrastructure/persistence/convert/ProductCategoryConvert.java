package com.rc.cloud.app.product.infrastructure.persistence.convert;

import com.rc.cloud.app.product.domain.category.ProductCategoryEntry;
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

    public ProductCategoryEntry convertToProductCategoryEntry(ProductCategory productCategory){
        return new ProductCategoryEntry(new ProductCategoryId( productCategory.getId().toString()));
    }
}
