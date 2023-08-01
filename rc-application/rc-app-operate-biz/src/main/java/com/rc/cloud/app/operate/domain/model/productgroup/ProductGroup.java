package com.rc.cloud.app.operate.domain.model.productgroup;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.specification.AppendProductGroupItemLimitSpecification;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductGroupErrorCodeConstants;
import com.rc.cloud.common.core.domain.AggregateRoot;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductGroup
 * @Author: liandy
 * @Date: 2023/7/8 16:26
 * @Description: 产品组合
 */
public class ProductGroup extends AggregateRoot {
    private ProductGroupId id;

    private String name;

    private ProductId productId;

    private List<ProductGroupItem> productGroupItems;
    private CreateTime createTime;
    public CreateTime getCreateTime() {
        return createTime;
    }


    public ProductGroup(ProductGroupId id, String name,TenantId tenantId,ProductId productId){
        setId(id);
        setName(name);
        setProductId(productId);
        this.createTime=new CreateTime(LocalDateTime.now());
        productGroupItems=new ArrayList<ProductGroupItem>();
    }
    public void setCreateTime(CreateTime createTime){
        this.createTime = createTime;
    }



    public int size(){
        return productGroupItems.size();
    }

    @Override
    public ProductGroupId getId() {
        return id;
    }

    public void setId(ProductGroupId id) {
        AssertUtils.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        AssertUtils.assertArgumentNotNull(name, "name must not be null");
        this.name = name;
    }
    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        AssertUtils.assertArgumentNotNull(productId, "productId must not be null");
        this.productId = productId;
    }

    public void clear(){
        productGroupItems.clear();
    }

    public boolean createItem(ProductGroupItem item){
        if(!new AppendProductGroupItemLimitSpecification().isSatisfiedBy(this)){
            throw new ServiceException(ProductGroupErrorCodeConstants.ProductGroupItemNumMoreThenLimit);
        }
        return productGroupItems.add(item);
    }

    public List<ProductGroupItem> getProductGroupItems(){
        return this.productGroupItems;
    }
}
