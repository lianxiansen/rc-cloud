package com.rc.cloud.app.product.domain.product.service;

import com.rc.cloud.app.product.domain.product.ProductEntry;
import com.rc.cloud.app.product.domain.product.ProductRepository;
import com.rc.cloud.app.product.domain.product.valobj.ProductId;
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

    public void execute(ProductId productId) {
        ProductEntry productEntry = productRepository.getProduct(productId);
        productEntry.disable();
        productRepository.saveProductEntry(productEntry);
    }
}
