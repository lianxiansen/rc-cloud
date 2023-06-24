package com.rc.cloud.app.product.domain.category;

import com.rc.cloud.app.product.appearance.request.ProductCategorySaveVO;
import com.rc.cloud.app.product.appearance.request.ProductCategoryVO;
import com.rc.cloud.app.product.domain.category.valobj.*;
import com.rc.cloud.app.product.infrastructure.persistence.po.ProductCategory;

import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
public interface ProductCategoryRepository{
    List<ProductCategoryEntry> getFirstList(ProductCategoryLocked locked, ProductCategoryLayer layer,ProductCategoryParent parent);

    ProductCategoryId nextId();

}
