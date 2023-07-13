package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuAttributeId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.AttributeValueCombination;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributePO;

import java.util.List;

public class ProductSkuAttributeConvert {

    public static ProductSkuAttribute convert(ProductSkuAttributePO po){
        ProductSkuAttribute productSkuAttribute=new ProductSkuAttribute(new ProductSkuAttributeId(po.getId()));
        String content = po.getContent();
        List<AttributeValueCombination> attributeValueCombinations = JSON.parseArray(content,AttributeValueCombination.class);
        productSkuAttribute.addAttributeValueCombinations(attributeValueCombinations);
        return productSkuAttribute;
    }

    public static ProductSkuAttributePO convert(ProductSkuAttribute productAttribute){
        ProductSkuAttributePO po=new ProductSkuAttributePO();
        po.setContent(JSON.toJSONString(productAttribute.getSkuAttributes()));

        return po;
    }

}
