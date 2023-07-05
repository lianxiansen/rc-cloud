package com.rc.cloud.app.operate.domain.model.product;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class ProductAttributeEntity  implements Comparable<ProductAttributeEntity>{

    private String attribute;

    private SortedSet<ProductAttributeValueEntity> values;

    private  Integer sortId;

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

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
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
