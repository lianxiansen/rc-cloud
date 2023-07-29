package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductSkuImageBO;
import com.rc.cloud.app.operate.application.dto.ProductSkuImageSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;

import java.util.ArrayList;
import java.util.List;

public class ProductSkuImageConvert {


    public static ProductSkuImage convert(ProductSkuImageSaveDTO dto){
        ProductSkuImage productSkuImage = new ProductSkuImage(new ProductSkuImageId(dto.getId()));
        productSkuImage.setUrl(new Url(dto.getUrl()));
        productSkuImage.setSort(new Sort(dto.getSort()));
        return productSkuImage;
    }

    public static List<ProductSkuImage> convertList(List<ProductSkuImageSaveDTO> list){
        List<ProductSkuImage> resList =new ArrayList<>();
        if(list!=null){
            for (ProductSkuImageSaveDTO productImageSaveDTO : list) {
                resList.add(convert(productImageSaveDTO));
            }
        }
        return resList;
    }


    public static ProductSkuImageBO convert(ProductSkuImage productSkuImage){
        ProductSkuImageBO bo =new ProductSkuImageBO();
        bo.setSort(productSkuImage.getSort().getValue());
        bo.setUrl(productSkuImage.getUrl().getValue());
        return bo;
    }

    public static List<ProductSkuImageBO> convertProductSkuImageBOList(List<ProductSkuImage> list){
        List<ProductSkuImageBO> resList =new ArrayList<>();
        for (ProductSkuImage productSkuImage : list) {
            resList.add(convert(productSkuImage));
        }
        return resList;
    }



}
