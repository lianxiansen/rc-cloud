package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductAndSkuBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.AttributeValueCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class ProductAndSkuConvert {


    public static ProductAndSkuBO convertProductAndSkuBO(Product product, ProductSku productSku) {
        ProductAndSkuBO bo=new ProductAndSkuBO();
        bo.setProductId(product.getId().id());
        bo.setProductName(product.getName().getValue());
        bo.setSkuId(productSku.getId().id());
        if(productSku.getPrice()!=null){
            bo.setPrice(productSku.getPrice().getValue());
        }
        if(productSku.getSkuImageList()!=null && productSku.getSkuImageList().size()>0){
            bo.setProductImage(productSku.getSkuImageList().get(0).getUrl().getValue());
        }else{
            bo.setProductImage(product.getProductListImage().getValue());
        }
        if( productSku.getProductSkuAttribute()!=null &&
                productSku.getProductSkuAttribute().getSkuAttributes()!=null){

            //{颜色,尺寸}          {黄色，Y}
             List<String> attributes=new ArrayList<>();

            //{颜色,尺寸}          {黄色，Y}
            List<String> attributeValues=new ArrayList<>();

            for (AttributeValueCombination skuAttribute : productSku.getProductSkuAttribute().getSkuAttributes()) {
                attributes.add(skuAttribute.getAttribute());
                attributeValues.add(skuAttribute.getAttributeValue());
            }
            bo.setAttributes(attributes);
            bo.setAttributeValues(attributeValues);
        }

        return bo;
    }
}
