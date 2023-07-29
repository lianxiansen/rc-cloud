package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;

public interface ProductDetailRepository {

    void insertProductDetail(ProductDetail productDetail);

    void updateProductDetail(ProductDetail productDetail);

    ProductDetail findByProductId(ProductId productId);

    void removeProductDetailByProductId(ProductId productId);
}
