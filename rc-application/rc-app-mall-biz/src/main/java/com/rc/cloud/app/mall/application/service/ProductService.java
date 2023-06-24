package com.rc.cloud.app.mall.application.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.rc.cloud.app.mall.application.data.ProductSaveDTO;
import com.rc.cloud.app.mall.domain.product.entity.ProductEntry;
import com.rc.cloud.app.mall.domain.product.entity.ProductImageEntry;
import com.rc.cloud.app.mall.domain.product.repository.ProductRepository;
import com.rc.cloud.app.mall.domain.product.service.ProductSaveService;
import com.rc.cloud.app.mall.domain.product.valobj.*;
import com.rc.cloud.app.mall.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ProductService
 * @Author: liandy
 * @Date: 2023/6/23 15:51
 * @Description: TODO
 */
@Service
public class ProductService {

    @Autowired
    private ProductSaveService productSaveService;

    public void saveProduct(ProductSaveDTO productSaveDTO){

        TenantId tenantId=new TenantId(productSaveDTO.getTenantId());
        ProductName productName=new ProductName(productSaveDTO.getProductName());
        productSaveService.execute(tenantId,ProductName,null,null);

    }


}
