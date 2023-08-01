package com.rc.cloud.app.operate.domain.model.productrecommend;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductRecommendErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: ProductRecommendDomainService
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: 产品推荐领域服务
 */
@Service
public class ProductRecommendServiceImpl implements ProductRecommendService {

    @Autowired
    private ProductRecommendRepository ProductRecommendRepository;

    @Resource
    private IdRepository idRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductRecommend create(ProductRecommend productRecommend) {
        Product product = productRepository.findById(productRecommend.getProductId());
        if (Objects.isNull(product)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        Product recommendProduct = productRepository.findById(productRecommend.getRecommendProductId());
        if (Objects.isNull(recommendProduct)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        ProductRecommendRepository.save(productRecommend);
        return productRecommend;
    }


    @Override
    public ProductRecommend findById(ProductRecommendId productRecommendId) {
        return ProductRecommendRepository.findById(productRecommendId);
    }

    @Override
    public boolean release(ProductRecommend productRecommend) {
        return ProductRecommendRepository.removeById(productRecommend.getId());
    }

    @Override
    public List<ProductRecommend> findListFromProduct(Product product) {
        return ProductRecommendRepository.findListByProductId(product.getId());
    }
}
