package com.rc.cloud.app.mall.infrastructure.persistence.convert;

import com.rc.cloud.app.mall.domain.category.entity.ProductCategoryEntry;
import com.rc.cloud.app.mall.domain.category.valobj.ProductCategoryId;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ProductCategory;

import java.util.List;

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
