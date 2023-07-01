package com.rc.cloud.app.operate.domain.model.product;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.Validate;

public class ProductAttributeValueEntity implements Comparable<ProductAttributeValueEntity> {


    private String attributeValue;

    private Integer sortId;

    public ProductAttributeValueEntity(String attributeValue, Integer sortId) {
        Validate.isTrue(StrUtil.isNotEmpty(attributeValue),"货号不能为空！");
        Validate.isTrue(sortId>0,"sortId不能为空！");
        this.attributeValue = attributeValue;
        this.sortId = sortId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public int hashCode() {
        return this.getSortId();
    }

    @Override
    public int compareTo(ProductAttributeValueEntity o) {
        if(o.sortId<this.sortId){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
