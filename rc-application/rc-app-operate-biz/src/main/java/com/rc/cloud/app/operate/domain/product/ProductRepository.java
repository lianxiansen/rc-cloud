package com.rc.cloud.app.operate.domain.product;

import com.rc.cloud.app.operate.domain.product.valobj.Id;
import com.rc.cloud.app.operate.domain.product.valobj.ImageId;

/**
 * @ClassName: ProductRepository
 * @Author: liandy
 * @Date: 2023/6/23 15:59
 * @Description: TODO
 */
public interface ProductRepository {
    void saveProductEntry(ProductEntity productEntry);
    ProductEntity getProduct(Id productId);
    Id nextProductId();
    ImageId nextProductImageId();
}
