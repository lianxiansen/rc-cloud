package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuAttributeId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.AttributeValueCombination;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuAttributePO;

import java.util.List;

public class ProductSkuAttributeConvert {

    public static ProductSkuAttribute convert(ProductSkuAttributePO po){
        if(po!=null){
            ProductSkuAttribute productSkuAttribute=new ProductSkuAttribute(new ProductSkuAttributeId(po.getId()));
            String content = po.getContent();
            List<AttributeValueCombination> attributeValueCombinations = JSON.parseArray(content,AttributeValueCombination.class);
            productSkuAttribute.addAttributeValueCombinations(attributeValueCombinations);
            return productSkuAttribute;
        }
        return null;
    }

    public static ProductSkuAttributePO convert(ProductSkuAttribute productAttribute){
        ProductSkuAttributePO po=new ProductSkuAttributePO();
        po.setContent(JSON.toJSONString(productAttribute.getSkuAttributes()));

        return po;
    }

}
