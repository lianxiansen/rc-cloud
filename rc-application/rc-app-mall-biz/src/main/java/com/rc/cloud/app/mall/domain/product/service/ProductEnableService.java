package com.rc.cloud.app.mall.domain.product.service;

import com.rc.cloud.app.mall.domain.product.entity.ProductEntry;
import com.rc.cloud.app.mall.domain.product.repository.ProductRepository;
import com.rc.cloud.app.mall.domain.product.valobj.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductDisableService
 * @Author: liandy
 * @Date: 2023/6/24 08:26
 * @Description: TODO
 */
@Service
public class ProductEnableService {
    @Autowired
    private ProductRepository productRepository;

    public void execute(ProductId productId) {
        ProductEntry productEntry = productRepository.getProduct(productId);
        productEntry.enable();
        productRepository.saveProductEntry(productEntry);
    }
}
