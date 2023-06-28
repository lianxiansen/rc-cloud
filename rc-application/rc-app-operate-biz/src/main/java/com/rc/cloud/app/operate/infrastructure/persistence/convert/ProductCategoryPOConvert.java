package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.category.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductCategoryPOConvert {
    public ProductCategoryPOConvert(){
    }

    public ProductCategoryAggregation convertToProductCategoryEntry(ProductCategoryDO productCategory){
        return new ProductCategoryAggregation(new ProductCategoryId( productCategory.getId().toString()));
    }
}
