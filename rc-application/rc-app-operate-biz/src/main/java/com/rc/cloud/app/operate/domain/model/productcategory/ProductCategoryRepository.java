package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;

import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
public interface ProductCategoryRepository {
    ProductCategory findById(ProductCategoryId productCategoryId);

    List<ProductCategory> findAll();

    boolean save(ProductCategory productCategory);
    boolean remove(ProductCategory productCategory);

    boolean removeById(ProductCategoryId productCategoryId);

    boolean existsByParentId(ProductCategoryId productCategoryId);

    boolean existsByName(String categoryName);

}
