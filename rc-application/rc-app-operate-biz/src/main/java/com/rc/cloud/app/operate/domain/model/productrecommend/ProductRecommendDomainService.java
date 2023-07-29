package com.rc.cloud.app.operate.domain.model.productrecommend;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;

import java.util.List;

public interface ProductRecommendDomainService {
    ProductRecommend create(ProductRecommend ProductRecommend);

    ProductRecommend findById(ProductRecommendId productRecommendId);

    boolean release(ProductRecommend productRecommend);

    List<ProductRecommend> findListFromProduct(Product product);
}
