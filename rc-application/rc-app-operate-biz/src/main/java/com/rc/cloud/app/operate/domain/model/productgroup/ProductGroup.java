package com.rc.cloud.app.operate.domain.model.productgroup;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName: ProductGroup
 * @Author: liandy
 * @Date: 2023/7/8 16:26
 * @Description: 产品组合
 */
public class ProductGroup extends Entity {
    public static final int MAX_ITEM_SIZE=10;
    private ProductGroupId id;

    private String name;

    private TenantId tenantId;

    private ProductId productId;

    private Collection<ProductGroupItem> productGroupItems;

    public CreateTime getCreateTime() {
        return createTime;
    }

    private CreateTime createTime;
    public ProductGroup(ProductGroupId id, String name,TenantId tenantId,ProductId productId){
        setId(id);
        setName(name);
        setTenantId(tenantId);
        setProductId(productId);
        this.createTime=new CreateTime(LocalDateTime.now());
        productGroupItems=new ArrayList<ProductGroupItem>();
    }



    public int size(){
        return productGroupItems.size();
    }

    public ProductGroupId getId() {
        return id;
    }

    public void setId(ProductGroupId id) {
        this.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.assertArgumentNotNull(name, "name must not be null");
        this.name = name;
    }
    private void setTenantId(TenantId tenantId) {
        this.assertArgumentNotNull(tenantId, "tenantId must not be null");
        this.tenantId = tenantId;
    }
    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.assertArgumentNotNull(productId, "productId must not be null");
        this.productId = productId;
    }

    public void remove(){
        productGroupItems.clear();
    }

    public boolean appendItem(ProductGroupItem item){
        if(productGroupItems.size()>=MAX_ITEM_SIZE){
            throw new DomainException("单个组合的产品数量不超过10");
        }
        return productGroupItems.add(item);
    }

    public Collection<ProductGroupItem> getProductGroupItemList(){
        return this.productGroupItems;
    }
}
