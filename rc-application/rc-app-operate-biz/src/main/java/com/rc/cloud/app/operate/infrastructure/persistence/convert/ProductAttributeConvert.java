package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductAttributeId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Attribute;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributePO;

import java.util.List;

public class ProductAttributeConvert {


    public static ProductAttribute convert(ProductAttributePO po){
        ProductAttribute productAttribute =new ProductAttribute(new ProductAttributeId(po.getId()));
        List<Attribute> attributes = JSON.parseArray(po.getContent(),Attribute.class);
        if(attributes!=null){
            productAttribute.addAttributeList(attributes);
        }
        return productAttribute;
    }

    public static ProductAttributePO convert(ProductAttribute productAttribute){
        ProductAttributePO productAttributePO =new ProductAttributePO();
        String attr = JSON.toJSONString(productAttribute.getAttributes());
        productAttributePO.setId(productAttribute.getId().id());
        productAttributePO.setContent(attr);
        return productAttributePO;
    }


}
