package com.rc.cloud.app.operate.domain.model.productsku.valobj;



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


import com.alibaba.fastjson.annotation.JSONField;
import com.rc.cloud.common.core.domain.ValueObject;

public class AttributeValueCombination implements ValueObject<AttributeValueCombination>, Comparable<AttributeValueCombination> {

    @JSONField(ordinal = 1, name = "attribute")
    private String attribute;


    @JSONField(ordinal = 1, name = "attributeValue")
    private String attributeValue;

    @JSONField(ordinal = 1, name = "sort")
    private  Integer sort;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public int hashCode() {
        return this.getAttribute().hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(AttributeValueCombination o) {
        if(o.sort<this.sort){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public boolean sameValueAs(AttributeValueCombination other) {
        return false;
    }
}
