package com.rc.cloud.app.operate.domain.model.product;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Attribute;
import com.rc.cloud.app.operate.domain.model.product.valobj.AttributeValue;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

import java.util.*;

/**
 *
 */
public class ProductAttribute extends ValueObject {


    public ProductAttribute(ProductId productId) {
        this.productId= productId;
        this.attributes = new TreeSet<>();
    }


    private ProductId productId;

    private SortedSet<Attribute> attributes;


    /**
     * 添加产品的属性
     * @param attribute 颜色
     * @param value 红
     * @param sort 99
     */
    public void addAttribute(String attribute,String value , int sort){
        AssertUtils.assertArgumentNotNull(attributes,"attributes must not be null");

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
        AssertUtils.assertArgumentNotNull(attributeList,"attributeList must not be null");
        for (Attribute attribute : attributeList) {
            for (AttributeValue value : attribute.getValues()) {
                addAttribute(attribute.getAttribute(),value.getAttributeValue(),value.getSort());
            }
        }
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public SortedSet<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * 判断两个对象相等的条件
     * 商品id相同
     * attributes相同
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        ProductAttribute that = (ProductAttribute) other;
        String attr1 = JSON.toJSONString(that.getAttributes());
        String attr2 = JSON.toJSONString(this.getAttributes());
        return Objects.equals(getProductId(), that.getProductId()) && attr1==attr2;
    }
}
