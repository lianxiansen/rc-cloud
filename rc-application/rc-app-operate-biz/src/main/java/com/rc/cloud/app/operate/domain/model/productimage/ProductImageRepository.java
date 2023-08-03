package com.rc.cloud.app.operate.domain.model.productimage;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;

import java.util.List;

public interface ProductImageRepository {

    List<ProductImage> getProductImageByProductId(ProductId productId, Integer imageType);

    void deleteProductImage(ProductImageId productImageId);

    void insertProductImage(ProductImage productImage);

    void updateProductImage(ProductImage productImage);

    void deleteProductImageByProductId(ProductId productId);
}
