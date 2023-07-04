package com.rc.cloud.app.operate.domain.model.productcategory.stub;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Layer;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Locked;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Parent;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName: ProductCategoryRepositoryStub
 * @Author: liandy
 * @Date: 2023/7/4 10:36
 * @Description: TODO
 */
public class ProductCategoryRepositoryStub  implements ProductCategoryRepository {
    @Override
    public List<ProductCategory> getFirstList(Locked locked, Layer layer, Parent parent) {
        return null;
    }

    @Override
    public ProductCategoryId nextId() {
        return new ProductCategoryId(UUID.randomUUID().toString().substring(0,32));
    }

    @Override
    public ProductCategory findById(ProductCategoryId productCategoryId) {
        return null;
    }

    @Override
    public List<ProductCategory> findAll() {
        return null;
    }

    @Override
    public void save(ProductCategory productCategory) {

    }

    @Override
    public void remove(ProductCategory productCategory) {

    }

    @Override
    public void removeById(ProductCategoryId productCategoryId) {

    }

    @Override
    public boolean existsChild(ProductCategoryId productCategoryId) {
        return true;
    }
}
