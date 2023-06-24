package com.rc.cloud.app.product.domain.product;

import com.rc.cloud.app.product.domain.DomainRegistry;
import com.rc.cloud.app.product.domain.common.Deleted;
import com.rc.cloud.app.product.domain.common.DomainEventPublisher;
import com.rc.cloud.app.product.domain.common.Entity;
import com.rc.cloud.app.product.domain.product.event.ProductCreatedEvent;
import com.rc.cloud.app.product.domain.product.valobj.*;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;

import java.util.List;

/**
 * @ClassName: ProductEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 商品
 */
public class ProductEntry extends Entity {
    private ProductId id;
    private TenantId tenantId;
    private ProductName productName;
    private ProductRemark productRemark;

    private Deleted deleted;
    private ProductEnable productEnable;
    private ProductType productType;

    private List<ProductImageEntry> productImages;
    protected ProductEntry(ProductId id, ProductType productType){
        this.id = id;
        this.productType=productType;
        DomainEventPublisher.instance().publish(new ProductCreatedEvent(tenantId, "test"));
    }
    public boolean exists(){
        return deleted.result();
    }
    public void setProductImages(List<ProductImageEntry> list){
        this.productImages = list;
    }

    public ProductId getId(){
        return id;
    }

    public void disable(){
        this.productEnable=new ProductEnable(false);
    }
    public void enable(){
        this.productEnable=new ProductEnable(true);
    }

    public boolean valid(){
        return exists()&&this.productEnable.result();
    }

    public void setProductName(ProductName productName){
        this.productName = productName;
    }

    public void setProductRemark(ProductRemark productRemark){
        this.productRemark = productRemark;
    }

    public void addProductImage(ProductImageEntry productImageEntry){
        productImages.add(productImageEntry);
    }
}
