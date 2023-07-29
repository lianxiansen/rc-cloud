package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;

import java.util.List;

public interface ProductCategoryDomainService {
    ProductCategory create(ProductCategory productCategory);

    boolean update(ProductCategory productCategory);

    List<ProductCategory> findAll();

    boolean remove(ProductCategory productCategory);

    void enable(ProductCategory productCategory);

    void disable(ProductCategory productCategory);

    ProductCategory findById(ProductCategoryId productCategoryId);
}
