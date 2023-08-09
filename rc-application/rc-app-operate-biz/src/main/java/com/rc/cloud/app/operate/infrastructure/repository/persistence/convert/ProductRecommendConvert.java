package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommend;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductRecommendPO;

import java.util.ArrayList;
import java.util.List;

public class ProductRecommendConvert {
    public static ProductRecommendPO convert2ProductRecommendPO(ProductRecommend productRecommend) {
        ProductRecommendPO productRecommendPO = new ProductRecommendPO();
        productRecommendPO.setId(productRecommend.getId().id());
        productRecommendPO.setProductId(productRecommend.getProductId().id());
        productRecommendPO.setRecommendProductId(productRecommend.getRecommendProductId().id());
        return productRecommendPO;
    }




    public static ProductRecommend convert2ProductRecommend(ProductRecommendPO productRecommendPO) {
        ProductRecommendId id = new ProductRecommendId(productRecommendPO.getId());
        ProductId productId = new ProductId(productRecommendPO.getProductId());
        ProductId recommendProductId=new ProductId(productRecommendPO.getRecommendProductId());
        ProductRecommend ProductRecommend = new ProductRecommend(id, productId,recommendProductId);
        return ProductRecommend;
    }
    public static List<ProductRecommend> convert2ProductRecommendBatch(List<ProductRecommendPO> pos) {
        List<ProductRecommend> productRecommends=new ArrayList<>();
        pos.forEach(po->{
            productRecommends.add(convert2ProductRecommend(po));
        });
        return productRecommends;
    }




}
