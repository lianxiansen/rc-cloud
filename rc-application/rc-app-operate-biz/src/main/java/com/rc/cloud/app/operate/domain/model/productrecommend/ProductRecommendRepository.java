package com.rc.cloud.app.operate.domain.model.productrecommend;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;

import java.util.List;

public interface ProductRecommendRepository {
    boolean save(ProductRecommend ProductRecommend);


    ProductRecommend findById(ProductRecommendId ProductRecommendId);

    boolean removeById(ProductRecommendId ProductRecommendId);


    List<ProductRecommend> findListByProductId(ProductId productId);
}
