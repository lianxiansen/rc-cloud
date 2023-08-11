package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductImageResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductSkuAttributeResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductSkuDetailResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductSkuImageResponse;
import com.rc.cloud.app.operate.application.bo.AttributeValueCombinationBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSkuConvert {


    public static ProductSkuDetailResponse convert2ProductSkuDetail(ProductSkuBO bo){

        ProductSkuDetailResponse response=new ProductSkuDetailResponse();
        response.setId(bo.getId());
        List<ProductSkuAttributeResponse> productSkuAttributeResponses=new ArrayList<>();
        if( bo.getSkuAttributes()!=null){
            for (AttributeValueCombinationBO skuAttribute : bo.getSkuAttributes()) {
                ProductSkuAttributeResponse attributeResponse=new ProductSkuAttributeResponse();
                attributeResponse.setName(skuAttribute.getAttribute());
                attributeResponse.setValue(skuAttribute.getAttributeValue());
                attributeResponse.setSort(skuAttribute.getSort());
                productSkuAttributeResponses.add(attributeResponse);
            }

        }
        response.setAttributes(productSkuAttributeResponses);
        if(bo.getSkuImages()!=null && bo.getSkuImages().size()>0){
            List<ProductSkuImageResponse> images = bo.getSkuImages().stream().map(item ->
                            new ProductSkuImageResponse(item.getUrl(), item.getSort()))
                    .collect(Collectors.toList());

            response.setAlbums(images);
        }
        response.setPrice(bo.getPrice().toString());
        response.setWeight(bo.getWeight().toString());
        response.setCartonSizeLength(bo.getCartonSizeLength());
        response.setCartonSizeWidth(bo.getCartonSizeWidth());
        response.setCartonSizeHeight(bo.getCartonSizeHeight());
        response.setPackingNumber(bo.getPackingNumber());
        response.setInventory(bo.getInventory());

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
