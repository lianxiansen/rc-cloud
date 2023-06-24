package com.rc.cloud.app.mall.domain.product.service;

import cn.hutool.core.util.ObjectUtil;
import com.rc.cloud.app.mall.domain.product.entity.ProductEntry;
import com.rc.cloud.app.mall.domain.product.entity.ProductImageEntry;
import com.rc.cloud.app.mall.domain.product.repository.ProductRepository;
import com.rc.cloud.app.mall.domain.product.valobj.*;
import com.rc.cloud.app.mall.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * @ClassName: ProductSaveOrUpdateService
 * @Author: liandy
 * @Date: 2023/6/24 09:18
 * @Description: TODO
 */
@Service
public class ProductSaveService {
    @Autowired
    private ProductRepository productRepository;


    public void execute(TenantId tenantId,ProductName productName,ProductRemark productRemark, ProductType productType) {
        ProductId productId = productRepository.nextProductId();
        ProductEntry productEntry=new ProductEntry(productId,new ProductType(0));
        productRepository.saveProductEntry(productEntry);

    }
}
