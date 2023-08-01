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

import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.AttributeValueCombination;
import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ProductSkuAttribute extends ValueObject {


    public ProductSkuAttribute(ProductSkuId productSkuId) {
        this.productSkuId =productSkuId;
        this.skuAttributes = new TreeSet<>();
    }

    private ProductSkuId productSkuId;

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

    public void addAttributeValueCombinations(List<AttributeValueCombination> attributeValueCombinations){
        AssertUtils.assertArgumentNotNull(attributeValueCombinations,"attributeValueCombinations must not be null");
        for (AttributeValueCombination attributeValueCombination : attributeValueCombinations) {
            addSkuAttribute(attributeValueCombination.getAttribute()
                    ,attributeValueCombination.getAttributeValue()
                    ,attributeValueCombination.getSort());
        }
    }


    public ProductSkuId getProductSkuId() {
        return productSkuId;
    }


    @Override
    public boolean equals(Object other) {
        return false;
    }
}
