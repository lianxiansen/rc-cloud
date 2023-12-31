package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.AttributeValueCombination;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuAttributePO;

import java.util.List;

public class ProductSkuAttributeConvert {

    public static ProductSkuAttribute convertDomain(ProductSkuAttributePO po){
        if(po!=null){
            ProductSkuAttribute productSkuAttribute=new ProductSkuAttribute(new ProductSkuId(po.getProductSkuId()));
            String content = po.getContent();
            List<AttributeValueCombination> attributeValueCombinations = JSON.parseArray(content,AttributeValueCombination.class);
            productSkuAttribute.addAttributeValueCombinations(attributeValueCombinations);
            return productSkuAttribute;
        }
        return null;
    }

    public static ProductSkuAttributePO convertPO(ProductSkuAttribute productAttribute ){
        ProductSkuAttributePO po=new ProductSkuAttributePO();
        po.setProductSkuId(productAttribute.getProductSkuId().id());
        po.setContent(JSON.toJSONString(productAttribute.getSkuAttributes()));

        return po;
    }

}
