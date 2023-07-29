package com.rc.cloud.app.operate.domain.model.productimage;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;

import java.util.List;

public interface ProductImageRepository {

    List<ProductImage> getProductImageByProductId(ProductId productId, Integer imageType);

    void removeProductImageByProductIdAndUrlAndSortAndType(ProductId productId,String url, int sort, int type);

    void insertProductImage(ProductImage productImage);

    void deleteProductImageByProductId(ProductId productId);
}
