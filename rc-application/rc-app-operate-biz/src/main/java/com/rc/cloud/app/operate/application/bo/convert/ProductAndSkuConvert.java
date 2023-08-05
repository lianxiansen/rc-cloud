package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductAndSkuBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.AttributeValueCombination;

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
            List<AttributeValueCombination> skuAttributes
                    = productSku.getProductSkuAttribute().getSkuAttributes().stream().collect(Collectors.toList());
            if(skuAttributes!=null){
                bo.setAttribute1(skuAttributes.get(0).getAttribute());
                bo.setAttribute1(skuAttributes.get(0).getAttributeValue());
            }
            if(skuAttributes!=null
                    && skuAttributes.size()==2){
                bo.setAttribute1(skuAttributes.get(0).getAttribute());
                bo.setAttributeValue1(skuAttributes.get(0).getAttributeValue());
                bo.setAttribute2(skuAttributes.get(1).getAttribute());
                bo.setAttributeValue2(skuAttributes.get(1).getAttributeValue());
            }
        }

        return bo;
    }
}
