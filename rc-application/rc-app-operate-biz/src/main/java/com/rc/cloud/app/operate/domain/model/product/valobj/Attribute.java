package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.alibaba.fastjson.annotation.JSONField;
import com.rc.cloud.app.operate.domain.common.ValueObject;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class Attribute extends ValueObject implements Comparable<Attribute>{


    @JSONField(ordinal = 1, name = "attribute")
    private String attribute;

    @JSONField(ordinal = 1, name = "values")
    private SortedSet<AttributeValue> values;

    @JSONField(ordinal = 1, name = "sort")
    private  Integer sort;

    public Attribute(){
        this.values=new TreeSet<>();

    }

    public void addValue(AttributeValue value){
        this.values.add(value);
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }


    public SortedSet<AttributeValue> getValues() {
        return values;
    }

    public void setValues(SortedSet<AttributeValue> values) {
        this.values = values;
    }


    @Override
    public int hashCode() {
        return this.getAttribute().hashCode();
    }


    @Override
    public int compareTo(Attribute o) {
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
