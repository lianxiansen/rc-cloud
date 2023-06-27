package com.rc.cloud.app.operate.domain.product.service;

import com.rc.cloud.app.operate.domain.product.ProductEntity;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.valobj.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductDisableService
 * @Author: liandy
 * @Date: 2023/6/24 08:26
 * @Description: TODO
 */
@Service
public class ProductDisableService {
    @Autowired
    private ProductRepository productRepository;

    public void execute(Id productId) {
        ProductEntity productEntry = productRepository.getProduct(productId);
        productEntry.disable();
        productRepository.saveProductEntry(productEntry);
    }
}
