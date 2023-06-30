package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductCategoryConvert {

    public static ProductCategoryAggregation convert2ProductCategoryAggregation(ProductCategoryDO source) {
        return null;
    }


    public static ProductCategoryDO convert2ProductCategoryDO(ProductCategoryAggregation source) {
        ProductCategoryDO target=new ProductCategoryDO();
        target.setId(source.getId().id());
        target.setProductCategoryPageImage(source.getPage().getCategoryImage());
        target.setProductListPageImage(source.getPage().getListImage());
        target.setLayer(source.getLayer().getValue());
        target.setEnabledFlag(source.getEnabled().value());
        target.setIcon(source.getIcon().getPictureUrl());
        target.setName(source.getName().getChName());
        target.setEnglishName(source.getName().getEnName());
        target.setParentId(source.getParentId()==null?null:source.getParentId().id());
        target.setSortId(source.getSort().getValue());
        target.setTenantId(source.getTenantId().id());
        return target;
    }
}
