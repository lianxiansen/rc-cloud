package com.rc.cloud.app.operate.domain.model.productrecommend;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.common.core.domain.AggregateRoot;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: ProductRecommend
 * @Author: liandy
 * @Date: 2023/7/8 16:26
 * @Description: 产品推荐
 */
public class ProductRecommend extends AggregateRoot {
    private ProductRecommendId id;
    private ProductId productId;
    private ProductId recommendProductId;


    public ProductRecommend(ProductRecommendId id, ProductId productId,ProductId recommendProductId){
        setId(id);
        setProductId(productId);
        setRecommendProductId(recommendProductId);
    }



    @Override
    public ProductRecommendId getId() {
        return id;
    }

    public void setId(ProductRecommendId id) {
        AssertUtils.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        AssertUtils.assertArgumentNotNull(productId, "productId must not be null");
        this.productId = productId;
    }

    public ProductId getRecommendProductId() {
        return recommendProductId;
    }

    public void setRecommendProductId(ProductId recommendProductId) {
        this.recommendProductId = recommendProductId;
    }
}
