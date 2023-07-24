package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;

import java.util.List;

public interface ProductCategoryDomainService {
    ProductCategory create(ProductCategoryBuildFactory.ProductCategoryBuilder builder);

    boolean update(ProductCategoryRebuildFactory.ProductCategoryRebuilder rebuilder);

    List<ProductCategory> findAll();

    boolean remove(ProductCategoryId productCategoryId);

    void enable(ProductCategory productCategory);

    void disable(ProductCategory productCategory);

    ProductCategory findById(ProductCategoryId productCategoryId);
}
