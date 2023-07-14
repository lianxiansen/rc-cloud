package com.rc.cloud.app.operate.domain.model.productdetail;

public interface ProductDetailRepository {
    String nextId();

    void saveProductDetail(ProductDetail productDetail);
}
