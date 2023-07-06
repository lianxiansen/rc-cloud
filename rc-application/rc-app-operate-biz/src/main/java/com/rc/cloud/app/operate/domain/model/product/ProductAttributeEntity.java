package com.rc.cloud.app.operate.domain.model.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.rc.cloud.app.operate.domain.common.ValueObject;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class ProductAttributeEntity implements Comparable<ProductAttributeEntity>{


    @JSONField(ordinal = 1, name = "attribute")
    private String attribute;

    @JSONField(ordinal = 1, name = "values")
    private SortedSet<ProductAttributeValueEntity> values;

    @JSONField(ordinal = 1, name = "sort")
    private  Integer sort;

    public ProductAttributeEntity(){
        this.values=new TreeSet<>();

    }

    public void addValue(ProductAttributeValueEntity productAttributeValueEntity){
        this.values.add(productAttributeValueEntity);
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }


    public SortedSet<ProductAttributeValueEntity> getValues() {
        return values;
    }

    public void setValues(SortedSet<ProductAttributeValueEntity> values) {
        this.values = values;
    }


    @Override
    public int hashCode() {
        return this.getAttribute().hashCode();
    }


    @Override
    public int compareTo(ProductAttributeEntity o) {
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
