package com.rc.cloud.app.operate.domain.product;

import com.rc.cloud.app.operate.domain.product.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
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
    private TenantService tenantService;
    public ProductEntity createProduct(TenantId tenantId, Name productName, Remark productRemark, Type productType, List<Image> productImages){
        validateTenantId(tenantId);
        Id productId = productRepository.nextProductId();
        ProductEntity productEntry=new ProductEntity(productId,new Type(0));
        productImages.forEach(item->{
            addProductImage(productEntry,item);
        });
        return productEntry;
    }

    private void validateTenantId(TenantId tenantId){
        if(!tenantService.exists(tenantId)){
            throw new IllegalArgumentException("所属租户错误");
        }
    }

    private void addProductImage(ProductEntity productEntry, Image productImage){
        if(!productEntry.isEnable()){
            throw new RuntimeException("商品已失效");
        }
        ProductImageEntity productImageEntry=new ProductImageEntity(productRepository.nextProductImageId(),
                productEntry.getId(),productImage);
        productEntry.addProductImage(productImageEntry);
    }


}
