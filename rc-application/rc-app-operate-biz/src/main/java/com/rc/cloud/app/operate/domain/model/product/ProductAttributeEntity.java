package com.rc.cloud.app.operate.domain.model.product;

import com.alibaba.fastjson.annotation.JSONField;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.common.ValueObject;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Attribute;
import com.rc.cloud.app.operate.domain.model.product.valobj.AttributeValue;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class ProductAttributeEntity extends Entity {


    public ProductAttributeEntity(String id, ProductId productId, TenantId tenantId) {
        this.id = id;
        this.productId = productId;
        this.tenantId = tenantId;
        this.attributes = new TreeSet<>();
    }

    private String id;

    private ProductId productId;

    private TenantId tenantId;

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
        }else{
            Attribute productAttributeEntity = first.get();
            productAttributeEntity.addValue(new AttributeValue(value,sort));
        }
    }

    public String getId() {
        return id;
    }

    public SortedSet<Attribute> getAttributes() {
        return attributes;
    }
}
