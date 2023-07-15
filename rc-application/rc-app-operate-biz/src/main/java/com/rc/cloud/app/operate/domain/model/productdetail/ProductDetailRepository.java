package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;

public interface ProductDetailRepository {
    String nextId();

    void saveProductDetail(ProductDetail productDetail);

    ProductDetail findById(ProductId productId);
}
