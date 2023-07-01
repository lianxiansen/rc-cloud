package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.product.ProductAggregation;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rc.cloud.common.core.util.AssertUtils.notNull;

/**
 * @ClassName: ProductValidateDomainService
 * @Author: liandy
 * @Date: 2023/7/1 10:32
 * @Description: TODO
 */
@Service
public class ValidateProductDomainService {
    @Autowired
    private ProductRepository productRepository;
    public void shouldExists(ProductId productId){
        notNull(productId, "productId is not null");
        ProductAggregation productAggregation= productRepository.findById(productId);
        if(null==productAggregation){
            throw new DomainException("商品唯一标识无效："+productId.id());
        }
    }



}
