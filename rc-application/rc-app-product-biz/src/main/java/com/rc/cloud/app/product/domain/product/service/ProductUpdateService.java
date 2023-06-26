package com.rc.cloud.app.product.domain.product.service;

import com.rc.cloud.app.product.domain.product.ProductEntity;
import com.rc.cloud.app.product.domain.product.ProductRepository;
import com.rc.cloud.app.product.domain.product.valobj.Id;
import com.rc.cloud.app.product.domain.product.valobj.Name;
import com.rc.cloud.app.product.domain.product.valobj.Remark;
import com.rc.cloud.app.product.domain.product.valobj.Type;
import com.rc.cloud.app.product.domain.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductUpdateService
 * @Author: liandy
 * @Date: 2023/6/24 09:18
 * @Description: TODO
 */
@Service
public class ProductUpdateService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TenantService tenantRepository;

    public void execute(Id productId, Name productName, Remark productRemark, Type productType) {
        ProductEntity productEntry= productRepository.getProduct(productId);
        productEntry.setProductName(productName);
        productEntry.setProductRemark(productRemark);
        productRepository.saveProductEntry(productEntry);
    }
}
