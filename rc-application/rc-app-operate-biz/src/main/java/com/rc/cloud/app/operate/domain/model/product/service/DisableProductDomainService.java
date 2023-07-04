package com.rc.cloud.app.operate.domain.model.product.service;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductDisableService
 * @Author: liandy
 * @Date: 2023/6/24 08:26
 * @Description: TODO
 */
@Service
public class DisableProductDomainService {
    @Autowired
    private ProductRepository productRepository;

    public void execute(ProductId productId) {
        Product productEntry = productRepository.findById(productId);
        productEntry.disable();
        productRepository.saveProductEntry(productEntry);
    }
}
