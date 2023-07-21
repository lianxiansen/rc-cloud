package com.rc.cloud.app.operate.domain.model.productgroup;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.common.core.domain.Entity;

import java.time.LocalDateTime;

/**
 * @ClassName: ProductGroupItem
 * @Author: liandy
 * @Date: 2023/7/8 16:31
 * @Description: 产品组合项
 */
public class ProductGroupItem extends Entity {
    private ProductGroupItemId id;
    private ProductGroupId productGroupId;

    private ProductId productId;
    private CreateTime createTime;
    public ProductId getProductId() {
        return productId;
    }




    public ProductGroupItem(ProductGroupItemId id,ProductGroupId productGroupId,ProductId productId){
        this.id=id;
        this.productGroupId = productGroupId;
        this.productId = productId;
        this.createTime=new CreateTime(LocalDateTime.now());
    }

    @Override
    public ProductGroupItemId getId() {
        return id;
    }



    public ProductGroupId getProductGroupId() {
        return productGroupId;
    }


    public CreateTime getCreateTime() {
        return createTime;
    }


}
