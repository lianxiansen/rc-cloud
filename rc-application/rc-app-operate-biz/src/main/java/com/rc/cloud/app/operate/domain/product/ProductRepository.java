package com.rc.cloud.app.operate.domain.product;

import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.product.identifier.ImageId;

/**
 * @ClassName: ProductRepository
 * @Author: liandy
 * @Date: 2023/6/23 15:59
 * @Description: TODO
 */
public interface ProductRepository {
    void saveProductEntry(ProductEntity productEntry);
    ProductEntity findById(ProductId productId);
    ProductId nextProductId();
    ImageId nextProductImageId();
}
