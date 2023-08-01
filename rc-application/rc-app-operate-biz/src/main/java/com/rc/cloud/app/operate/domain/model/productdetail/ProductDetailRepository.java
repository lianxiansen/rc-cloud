package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.identifier.ProductDetailId;

public interface ProductDetailRepository {

    void insertProductDetail(ProductDetail productDetail);

    void updateProductDetail(ProductDetail productDetail);

    ProductDetail findById(ProductDetailId productDetailId);

    void removeProductDetail(ProductDetailId productDetailId);
}
