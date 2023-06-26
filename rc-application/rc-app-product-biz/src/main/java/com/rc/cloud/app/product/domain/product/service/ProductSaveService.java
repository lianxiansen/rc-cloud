package com.rc.cloud.app.product.domain.product.service;

import com.rc.cloud.app.product.domain.product.ProductEntity;
import com.rc.cloud.app.product.domain.product.ProductFactory;
import com.rc.cloud.app.product.domain.product.ProductRepository;
import com.rc.cloud.app.product.domain.product.valobj.*;
import com.rc.cloud.app.product.domain.tenant.service.TenantService;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProductSaveService
 * @Author: liandy
 * @Date: 2023/6/24 09:18
 * @Description: TODO
 */
@Service
public class ProductSaveService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private ProductFactory productFactory;

    public void execute(TenantId tenantId, ProductName productName, ProductRemark productRemark, ProductType productType,
                        List<ProductImage> productImages) {
        ProductEntity productEntry= productFactory.createProduct(tenantId,productName,productRemark,productType,productImages);
        productRepository.saveProductEntry(productEntry);
    }
}
