package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Attribute;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributePO;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class ProductAttributeConvert {


    public static ProductAttribute convert(ProductAttributePO po){
        ProductAttribute productAttribute =new ProductAttribute(po.getId());
        List<Attribute> attributes = JSON.parseArray(po.getContent(),Attribute.class);
        if(attributes!=null){
            productAttribute.addAttributeList(attributes);
        }
        return productAttribute;
    }

    public static ProductAttributePO convert(ProductAttribute productAttribute){
        ProductAttributePO productAttributePO =new ProductAttributePO();
        String attr = JSON.toJSONString(productAttribute.getAttributes());
        productAttributePO.setId(productAttribute.getId());
        productAttributePO.setContent(attr);
        return productAttributePO;
    }

    
}
