package com.rc.cloud.app.operate.domain.model.product.valobj;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.rc.cloud.common.core.domain.ValueObject;
import org.apache.commons.lang3.Validate;

public class AttributeValue extends ValueObject implements Comparable<AttributeValue>{

    @JSONField(ordinal = 1, name = "attributeValue")
    private String attributeValue;

    @JSONField(ordinal = 1, name = "sort")
    private Integer sort;

    public AttributeValue(String attributeValue, Integer sort) {
        Validate.isTrue(StrUtil.isNotEmpty(attributeValue),"货号不能为空！");
        Validate.isTrue(sort>=0,"sort不能为空！");
        this.attributeValue = attributeValue;
        this.sort = sort;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }


    @Override
    public int hashCode() {
        return this.getAttributeValue().hashCode();
    }

    @Override
    public int compareTo(AttributeValue o) {
        if(o.sort<this.sort){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
