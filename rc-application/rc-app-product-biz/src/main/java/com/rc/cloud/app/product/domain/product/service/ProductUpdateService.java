package com.rc.cloud.app.product.domain.product.service;

import com.rc.cloud.app.product.domain.product.ProductEntry;
import com.rc.cloud.app.product.domain.product.ProductRepository;
import com.rc.cloud.app.product.domain.product.valobj.ProductId;
import com.rc.cloud.app.product.domain.product.valobj.ProductName;
import com.rc.cloud.app.product.domain.product.valobj.ProductRemark;
import com.rc.cloud.app.product.domain.product.valobj.ProductType;
import com.rc.cloud.app.product.domain.tenant.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductSaveOrUpdateService
 * @Author: liandy
 * @Date: 2023/6/24 09:18
 * @Description: TODO
 */
@Service
public class ProductUpdateService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TenantRepository tenantRepository;

    public void execute(ProductId productId,ProductName productName, ProductRemark productRemark, ProductType productType) {
        ProductEntry productEntry= productRepository.getProduct(productId);
        productEntry.setProductName(productName);
        productEntry.setProductRemark(productRemark);
        productRepository.saveProductEntry(productEntry);
    }
}
