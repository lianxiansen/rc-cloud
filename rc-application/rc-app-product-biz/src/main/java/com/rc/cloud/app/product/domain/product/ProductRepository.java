package com.rc.cloud.app.product.domain.product;

import com.rc.cloud.app.product.domain.product.valobj.ProductId;
import com.rc.cloud.app.product.domain.product.valobj.ProductImageId;

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
