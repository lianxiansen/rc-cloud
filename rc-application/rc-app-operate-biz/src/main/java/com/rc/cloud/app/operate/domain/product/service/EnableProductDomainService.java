package com.rc.cloud.app.operate.domain.product.service;

import com.rc.cloud.app.operate.domain.product.ProductAggregation;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductDisableService
 * @Author: liandy
 * @Date: 2023/6/24 08:26
 * @Description: TODO
 */
@Service
public class EnableProductDomainService {
    @Autowired
    private ProductRepository productRepository;

    public void execute(ProductId productId) {
        ProductAggregation productEntry = productRepository.findById(productId);
        productEntry.enable();
        productRepository.saveProductEntry(productEntry);
    }
}
