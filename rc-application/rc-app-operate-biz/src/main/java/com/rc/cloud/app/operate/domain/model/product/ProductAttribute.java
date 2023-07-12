package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductAttributeId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Attribute;
import com.rc.cloud.app.operate.domain.model.product.valobj.AttributeValue;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class ProductAttribute extends Entity {


    public ProductAttribute(ProductAttributeId id, ProductId productId, TenantId tenantId) {
        this.id = id;
        this.productId = productId;
        this.tenantId = tenantId;
        this.attributes = new TreeSet<>();
    }

    private ProductAttributeId id;

    private ProductId productId;

    private TenantId tenantId;

    private SortedSet<Attribute> attributes;


    /**
     * 添加产品的属性
     * @param attribute 颜色
     * @param value 红
     * @param sort 99
     */
    public void addAttribute(ProductAttributeId attribute,String value , int sort){
        AssertUtils.assertArgumentNotNull(attributes,"attributes must not be null");

        Optional<Attribute> first = attributes.stream().filter(u -> u.getAttribute().equals(attribute)).findFirst();
        if(!first.isPresent()){
            Attribute entity=new Attribute();
            entity.setAttribute(attribute.id());
            entity.setSort(sort);
            entity.addValue(new AttributeValue(value,sort));
            attributes.add(entity);
        }else{
            Attribute attribute1 = first.get();
            attribute1.addValue(new AttributeValue(value,sort));
        }
    }

    @Override
    public ProductAttributeId getId() {
        return id;
    }

    public SortedSet<Attribute> getAttributes() {
        return attributes;
    }
}
