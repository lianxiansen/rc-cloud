package com.rc.cloud.app.operate.domain.model.productrecommend;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProductRecommendDomainService
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: 产品推荐领域服务
 */
@Service
public class ProductRecommendService {

    @Autowired
    private ProductRecommendRepository ProductRecommendRepository;

    public ProductRecommend create(ProductRecommend productRecommend) {

        ProductRecommendRepository.save(productRecommend);
        return productRecommend;
    }



    public ProductRecommend findById(ProductRecommendId productRecommendId) {
        return ProductRecommendRepository.findById(productRecommendId);
    }


    public boolean release(ProductRecommend productRecommend) {
        return ProductRecommendRepository.removeById(productRecommend.getId());
    }


    public List<ProductRecommend> findListByProduct(Product product) {
        return ProductRecommendRepository.findListByProductId(product.getId());
    }
}
