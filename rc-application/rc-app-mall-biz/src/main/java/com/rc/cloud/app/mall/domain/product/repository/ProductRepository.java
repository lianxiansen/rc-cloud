package com.rc.cloud.app.mall.domain.product.repository;

import com.rc.cloud.app.mall.domain.category.valobj.ProductCategoryId;
import com.rc.cloud.app.mall.domain.product.entity.ProductEntry;
import com.rc.cloud.app.mall.domain.product.valobj.ProductId;
import com.rc.cloud.app.mall.domain.product.valobj.ProductImageId;

/**
 * @ClassName: ProductRepository
 * @Author: liandy
 * @Date: 2023/6/23 15:59
 * @Description: TODO
 */
public interface ProductRepository {
    void saveProductEntry(ProductEntry productEntry);
    ProductEntry getProduct(ProductId productId);
    ProductId nextProductId();
    ProductImageId nextProductImageId();
}
