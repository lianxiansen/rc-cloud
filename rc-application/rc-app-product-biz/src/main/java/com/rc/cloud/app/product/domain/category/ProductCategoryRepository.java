package com.rc.cloud.app.product.domain.category;

import com.rc.cloud.app.product.domain.category.valobj.*;

import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
public interface ProductCategoryRepository{
    List<ProductCategoryEntity> getFirstList(ProductCategoryLocked locked, ProductCategoryLayer layer, ProductCategoryParent parent);

    ProductCategoryId nextId();

}
