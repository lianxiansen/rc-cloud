package com.rc.cloud.app.operate.domain.category;

import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.category.valobj.*;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategory;

import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
public interface ProductCategoryRepository{
    List<ProductCategoryEntity> getFirstList(Locked locked, Layer layer, Parent parent);

    ProductCategoryId nextId();

    ProductCategory findById(ProductCategoryId productCategoryId);
}
