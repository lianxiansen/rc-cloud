package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductSkuDetailResponse;
import com.rc.cloud.app.operate.application.bo.AttributeValueCombinationBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;

import java.util.ArrayList;
import java.util.List;

public class ProductSkuConvert {


    public static ProductSkuDetailResponse convert2ProductSkuDetail(ProductSkuBO bo){

        ProductSkuDetailResponse response=new ProductSkuDetailResponse();
        if( bo.getSkuAttributes()!=null){
            if(bo.getSkuAttributes().size()==1){
                AttributeValueCombinationBO a1 = bo.getSkuAttributes().get(0);
                response.setAttribute1(a1.getAttribute());
                response.setAttributeValue1(a1.getAttributeValue());

            }else if(bo.getSkuAttributes().size()==2){
                AttributeValueCombinationBO a1 = bo.getSkuAttributes().get(0);
                AttributeValueCombinationBO a2 = bo.getSkuAttributes().get(1);
                response.setAttribute1(a1.getAttribute());
                response.setAttributeValue1(a1.getAttributeValue());
                response.setAttribute2(a2.getAttribute());
                response.setAttributeValue2(a2.getAttributeValue());
            }
        }
        if(bo.getSkuImages()!=null && bo.getSkuImages().size()>0){
            response.setSkuImage(bo.getSkuImages().get(0).getUrl());
        }
        response.setPrice(bo.getPrice().toString());
        response.setWeight(bo.getWeight().toString());
        response.setCartonSizeLength(bo.getCartonSizeLength());
        response.setCartonSizeWidth(bo.getCartonSizeWidth());
        response.setCartonSizeHeight(bo.getCartonSizeHeight());
        response.setPackingNumber(bo.getPackingNumber());


        return response;

    }

    public static List<ProductSkuDetailResponse> convert2ProductSkuDetailList(List<ProductSkuBO> boList){
        List<ProductSkuDetailResponse> responseList=new ArrayList<>();
        boList.forEach(x->
                responseList.add(convert2ProductSkuDetail(x))
                );
        return responseList;
    }
}
