package com.rc.cloud.app.product.domain.product;

import com.rc.cloud.app.product.domain.product.valobj.*;
import com.rc.cloud.app.product.domain.tenant.TenantRepository;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: ProductFactory
 * @Author: liandy
 * @Date: 2023/6/24 09:56
 * @Description: TODO
 */
@Component
public class ProductFactory {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TenantRepository tenantRepository;

    public ProductEntry createProduct(TenantId tenantId, ProductName productName, ProductRemark productRemark, ProductType productType, List<ProductImage> productImages){
        validateTenantId(tenantId);
        ProductId productId = productRepository.nextProductId();
        ProductEntry productEntry=new ProductEntry(productId,new ProductType(0));
        productImages.forEach(item->{
            productEntry.addProductImage(productRepository,item);
        });
        return productEntry;
    }

    private void validateTenantId(TenantId tenantId){
        if(!tenantRepository.exists(tenantId)){
            throw new IllegalArgumentException("所属租户错误");
        }
    }


}
