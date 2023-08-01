package com.rc.cloud.app.operate.domain.model.productsku.valobj;




import com.alibaba.fastjson.annotation.JSONField;
import com.rc.cloud.common.core.domain.ValueObject;

public class AttributeValueCombination extends ValueObject  implements Comparable<AttributeValueCombination> {

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
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            AttributeValueCombination typedObject = (AttributeValueCombination) other;
        }
        return false;
    }
}
