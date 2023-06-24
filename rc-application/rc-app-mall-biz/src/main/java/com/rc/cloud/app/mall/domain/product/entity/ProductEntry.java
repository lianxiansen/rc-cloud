package com.rc.cloud.app.mall.domain.product.entity;

import com.rc.cloud.app.mall.domain.category.valobj.*;
import com.rc.cloud.app.mall.domain.common.Deleted;
import com.rc.cloud.app.mall.domain.common.Entity;
import com.rc.cloud.app.mall.domain.common.ProductEnable;
import com.rc.cloud.app.mall.domain.product.valobj.ProductId;
import com.rc.cloud.app.mall.domain.product.valobj.ProductName;
import com.rc.cloud.app.mall.domain.product.valobj.ProductRemark;
import com.rc.cloud.app.mall.domain.product.valobj.ProductType;
import com.rc.cloud.app.mall.domain.tenant.valobj.TenantId;

import java.util.List;
import java.util.Set;

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
    public ProductEntry(ProductId id, ProductType productType){
        this.id = id;
        this.productType=productType;
    }
    public boolean exists(){
        return deleted.getFlag();
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
}
