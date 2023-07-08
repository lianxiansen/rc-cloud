package com.rc.cloud.app.operate.domain.model.productsku;



//        -- 商品SKU属性表
//        -- 存储[
//        -- {
//        --                     "attribute_name" : "颜色",
//        --                     "attribute_value" : "卡其色",
//        --                     "sort_id" : 42
//        --                 },
//        --                 {
//        --                     "attribute_name" : "尺码",
//        --                     "attribute_value" : "44",
//        --                     "sort_id" : 42
//        --                 }
//        --             ]


import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.AttributeValueCombination;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.SortedSet;
import java.util.TreeSet;

public class ProductSkuAttributeEntity extends Entity {


    public ProductSkuAttributeEntity(String id,ProductSkuId productSkuId, TenantId tenantId) {
        this.id =id;
        this.productSkuId = productSkuId;
        this.tenantId = tenantId;
        this.skuAttributes = new TreeSet<>();
    }

    private String id;

    private ProductSkuId productSkuId;

    private TenantId tenantId;

    private SortedSet<AttributeValueCombination> skuAttributes;

    public SortedSet<AttributeValueCombination> getSkuAttributes() {
        return skuAttributes;
    }

    public void addSkuAttribute(String name, String value, int sort){
        AttributeValueCombination entity=new AttributeValueCombination();
        entity.setAttribute(name);
        entity.setAttributeValue(value);
        entity.setSort(sort);
        skuAttributes.add(entity);
    }

    public String getId() {
        return id;
    }

}
