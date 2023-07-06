package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductCategoryDOConvert {

    public static ProductCategoryDO convert2ProductCategoryDO(ProductCategory source) {
        ProductCategoryDO target=new ProductCategoryDO();
        target.setId(source.getId().id());
        target.setProductCategoryPageImage(source.getPage().getCategoryImage());
        target.setProductListPageImage(source.getPage().getListImage());
        target.setLayer(source.getLayer().getValue());
        target.setEnabledFlag(source.getEnabled().value());
        target.setIcon(source.getIcon().getPictureUrl());
        target.setName(source.getChName().value());
        target.setEnglishName(source.getEnName().value());
        target.setParentId(source.getParentId()==null?null:source.getParentId().id());
        target.setSortId(source.getSort().getValue());
        target.setTenantId(source.getTenantId().id());
        return target;
    }
}