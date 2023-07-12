package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Layer;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Locked;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Parent;

import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
public interface ProductCategoryRepository {
    List<ProductCategory> getFirstList(Locked locked, Layer layer, Parent parent);

    ProductCategoryId nextId();

    ProductCategory findById(ProductCategoryId productCategoryId);

    List<ProductCategory> findAll();

    boolean save(ProductCategory productCategory);
    boolean remove(ProductCategory productCategory);

    boolean removeById(ProductCategoryId productCategoryId);

    boolean existsByParentId(ProductCategoryId productCategoryId);
}
