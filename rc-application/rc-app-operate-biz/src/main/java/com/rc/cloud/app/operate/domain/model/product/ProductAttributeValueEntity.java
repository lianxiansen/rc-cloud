package com.rc.cloud.app.operate.domain.model.product;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.Validate;

public class ProductAttributeValueEntity implements Comparable<ProductAttributeValueEntity> {

    @JSONField(ordinal = 1, name = "attributeValue")
    private String attributeValue;

    @JSONField(ordinal = 1, name = "sort")
    private Integer sort;

    public ProductAttributeValueEntity(String attributeValue, Integer sort) {
        Validate.isTrue(StrUtil.isNotEmpty(attributeValue),"货号不能为空！");
        Validate.isTrue(sort>0,"sort不能为空！");
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
    public int compareTo(ProductAttributeValueEntity o) {
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
}
