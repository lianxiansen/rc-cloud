package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Attribute;
import com.rc.cloud.app.operate.domain.model.product.valobj.AttributeValue;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class ProductAttribute extends Entity {


    public ProductAttribute(String id) {
        this.id = id;
        this.attributes = new TreeSet<>();
    }

    private String id;

    private SortedSet<Attribute> attributes;


    /**
     * 添加产品的属性
     * @param attribute 颜色
     * @param value 红
     * @param sort 99
     */
    public void addAttribute(String attribute,String value , int sort){
        this.assertArgumentNotNull(attributes,"attributes must not be null");

        Optional<Attribute> first = attributes.stream().filter(u -> u.getAttribute().equals(attribute)).findFirst();
        if(!first.isPresent()){
            Attribute entity=new Attribute();
            entity.setAttribute(attribute);
            entity.setSort(sort);
            entity.addValue(new AttributeValue(value,sort));
            attributes.add(entity);
        }else{
            Attribute attribute1 = first.get();
            attribute1.addValue(new AttributeValue(value,sort));
        }
    }

    /**
     * 批量添加产品的属性
     * @param attributeList
     */
    public void addAttributeList(List<Attribute> attributeList){
        this.assertArgumentNotNull(attributeList,"attributeList must not be null");
        for (Attribute attribute : attributeList) {
            for (AttributeValue value : attribute.getValues()) {
                addAttribute(attribute.getAttribute(),value.getAttributeValue(),value.getSort());
            }
        }
    }

    public String getId() {
        return id;
    }

    public SortedSet<Attribute> getAttributes() {
        return attributes;
    }
}
