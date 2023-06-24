package com.rc.cloud.app.product.domain.product;

import com.rc.cloud.app.product.domain.product.valobj.ProductId;
import com.rc.cloud.app.product.domain.product.valobj.ProductName;
import com.rc.cloud.app.product.domain.product.valobj.ProductRemark;
import com.rc.cloud.app.product.domain.product.valobj.ProductType;
import com.rc.cloud.app.product.domain.tenant.TenantRepository;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;

/**
 * @ClassName: ProductFactory
 * @Author: liandy
 * @Date: 2023/6/24 09:56
 * @Description: TODO
 */
public class ProductFactory {
    private ProductRepository productRepository;
    private TenantRepository tenantRepository;
    public ProductFactory(ProductRepository productRepository,TenantRepository tenantRepository){
        this.productRepository = productRepository;
        this.tenantRepository = tenantRepository;
    }

    public ProductEntry createProduct(TenantId tenantId, ProductName productName, ProductRemark productRemark, ProductType productType){
        validateTenantId(tenantId);
        ProductId productId = productRepository.nextProductId();
        ProductEntry productEntry=new ProductEntry(productId,new ProductType(0));
        return productEntry;
    }

    private void validateTenantId(TenantId tenantId){
        if(!tenantRepository.exists(tenantId)){
            throw new IllegalArgumentException("所属租户错误");
        }
    }


}
