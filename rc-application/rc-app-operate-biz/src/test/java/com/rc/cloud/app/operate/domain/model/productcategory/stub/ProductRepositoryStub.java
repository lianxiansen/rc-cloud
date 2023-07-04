package com.rc.cloud.app.operate.domain.model.productcategory.stub;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.List;

/**
 * @ClassName: ProductRepositoryStub
 * @Author: liandy
 * @Date: 2023/7/4 10:03
 * @Description: TODO
 */
public class ProductRepositoryStub implements ProductRepository {
    @Override
    public void saveProductEntry(ProductAggregation productEntry) {

    }


    @Override
    public ProductAggregation findById(ProductId productId) {
        return null;
    }

    @Override
    public ProductId nextProductId() {
        return null;
    }

    @Override
    public ProductImageId nextProductImageId() {
        return null;
    }

    @Override
    public PageResult<ProductDO> getProductPageList(ProductListQueryDTO productListQueryDTO) {
        return null;
    }

    @Override
    public List<ProductDictDO> getProductDictByProductId(ProductId productId) {
        return null;
    }

    @Override
    public List<ProductImageDO> getProductImageByProductId(ProductId productId) {
        return null;
    }

    @Override
    public boolean existsByProductCategoryId(ProductCategoryId productCategoryId) {
        return true;
    }
}
