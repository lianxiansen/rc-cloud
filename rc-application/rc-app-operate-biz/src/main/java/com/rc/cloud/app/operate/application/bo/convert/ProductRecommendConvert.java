package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommend;
import com.rc.cloud.common.core.util.AssertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName: ProductRecommendConvert
 * @Author: liandy
 * @Date: 2023/7/15 15:02
 * @Description: TODO
 */
public class ProductRecommendConvert {
    public static List<ProductRecommendBO> convert2ProductRecommendBOBatch(List<ProductRecommend> ProductRecommendList, List<Product> products) {
        List<ProductRecommendBO> boList = new ArrayList<>();
        ProductRecommendList.forEach(productRecommend -> {
            Optional<Product> product = products.stream().filter(p -> p.getId().equals(productRecommend.getRecommendProductId())).findFirst();
            if(product.isPresent()){
                ProductRecommendBO bo = convert2ProductRecommendBO(productRecommend, product.get());
                boList.add(bo);
            }

        });
        return boList;
    }

    public static ProductRecommendBO convert2ProductRecommendBO(ProductRecommend productRecommend, Product recommendProduct) {
        AssertUtils.assertArgumentEquals(productRecommend.getRecommendProductId(), recommendProduct.getId(), "推荐产品的唯一标识与产品不一致");
        ProductRecommendBO productRecommendBO = new ProductRecommendBO();
        productRecommendBO.setId(productRecommend.getId().id())
                .setProductId(productRecommend.getProductId().id())
                .setRecommendProductId(productRecommend.getRecommendProductId().id());
        if (Objects.nonNull(recommendProduct)) {
            productRecommendBO.setRecommendProductName(recommendProduct.getName().getValue())
                .setRecommendProductImage(Objects.isNull(recommendProduct.getProductListImage()) ? "" : recommendProduct.getProductListImage().getValue());
        }

        return productRecommendBO;
    }
}
