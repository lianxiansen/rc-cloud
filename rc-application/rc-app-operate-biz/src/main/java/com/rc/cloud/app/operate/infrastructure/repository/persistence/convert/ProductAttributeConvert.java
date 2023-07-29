package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductAttributeId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Attribute;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductAttributePO;

import java.util.List;

public class ProductAttributeConvert {


    public static ProductAttribute convertDomain(ProductAttributePO po){
       if(po!=null){
           ProductAttribute productAttribute =new ProductAttribute(new ProductId(po.getProductId()));
           List<Attribute> attributes = JSON.parseArray(po.getContent(),Attribute.class);
           if(attributes!=null){
               productAttribute.addAttributeList(attributes);
           }
           return productAttribute;
       }
       return null;
    }

    public static ProductAttributePO convertProductAttributePO(ProductAttribute productAttribute){
        ProductAttributePO productAttributePO =new ProductAttributePO();
        String attr = JSON.toJSONString(productAttribute.getAttributes());
        productAttributePO.setProductId(productAttribute.getProductId().id());
        productAttributePO.setContent(attr);
        return productAttributePO;
    }


}
