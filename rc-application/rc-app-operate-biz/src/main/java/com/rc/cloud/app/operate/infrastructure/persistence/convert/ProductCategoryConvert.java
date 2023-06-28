package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.category.ProductCategoryAggregation;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;
import org.mapstruct.factory.Mappers;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductCategoryConvert {
    public static ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);

    public ProductCategoryAggregation convert2ProductCategoryDO(ProductCategoryDO a) {
        return null;
    }


    public ProductCategoryDO convert2ProductCategoryAggregation(ProductCategoryAggregation a) {
        return null;
    }
}
