package com.rc.cloud.app.product.application.service;

import com.rc.cloud.app.product.application.data.ProductSaveDTO;
import com.rc.cloud.app.product.domain.product.service.ProductSaveService;
import com.rc.cloud.app.product.domain.product.service.ProductUpdateService;
import com.rc.cloud.app.product.domain.product.valobj.*;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private ProductUpdateService productUpdateService;
    public void saveOrUpdateProduct(ProductSaveDTO productSaveDTO){
        TenantId tenantId=new TenantId(productSaveDTO.getTenantId());
        ProductName productName=new ProductName(productSaveDTO.getProductName());
        if(productSaveDTO.getId()>0){
            productSaveService.execute(tenantId,productName,null,null);
        }else{
            productUpdateService.execute(new ProductId(productSaveDTO.getId()+""),productName,null,null);
        }


    }


}
