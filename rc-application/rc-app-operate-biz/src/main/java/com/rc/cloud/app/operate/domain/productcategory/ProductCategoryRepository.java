package com.rc.cloud.app.operate.domain.productcategory;

import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Locked;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Parent;
import com.rc.cloud.app.operate.domain.category.valobj.Layer;

import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
public interface ProductCategoryRepository {
    List<ProductCategoryAggregation> getFirstList(Locked locked, Layer layer, Parent parent);

    ProductCategoryId nextId();

    ProductCategoryAggregation findById(ProductCategoryId productCategoryId);

    List<ProductCategoryAggregation> findAll();

    void save(ProductCategoryAggregation productCategoryAggregation);
}
