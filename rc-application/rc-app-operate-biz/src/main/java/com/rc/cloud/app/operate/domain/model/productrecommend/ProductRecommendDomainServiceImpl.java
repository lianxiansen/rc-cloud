package com.rc.cloud.app.operate.domain.model.productrecommend;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ErrorCodeConstants;
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
public class ProductRecommendDomainServiceImpl implements ProductRecommendDomainService {

    @Autowired
    private ProductRecommendRepository ProductRecommendRepository;
    @Autowired
    private ProductRepository productRepository;
    @Resource
    private IdRepository idRepository;

    @Override
    public ProductRecommend create(TenantId tenantId, ProductId productId, ProductId recommendProductId) {
        Product product = productRepository.findById(productId);
        if (Objects.isNull(product)) {
            throw new ServiceException(ProductRecommendErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        ProductRecommend ProductRecommend = new ProductRecommend(new ProductRecommendId(idRepository.nextId()), tenantId, productId,recommendProductId);
        ProductRecommendRepository.save(ProductRecommend);
        return ProductRecommend;
    }



    @Override
    public ProductRecommend findById(ProductRecommendId productRecommendId) {
        return ProductRecommendRepository.findById(productRecommendId);
    }

    @Override
    public boolean release(ProductRecommendId productRecommendId) {
        ProductRecommend productRecommend = this.findById(productRecommendId);
        if (Objects.isNull(productRecommend)) {
            throw new ServiceException(ErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        return ProductRecommendRepository.removeById(productRecommendId);
    }

    @Override
    public List<ProductRecommend> findListByProductId(ProductId productId){
        return ProductRecommendRepository.findListByProductId(productId);
    }
}
