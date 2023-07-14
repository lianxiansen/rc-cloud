package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.dto.ProductSkuImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuImageSaveDTO;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;

import java.util.ArrayList;
import java.util.List;

public class ProductSkuImageConvert {


    public static ProductSkuImage convert(ProductSkuImageSaveDTO dto){
        ProductSkuImage productSkuImage = new ProductSkuImage(new ProductSkuImageId(dto.getId()));
        productSkuImage.setUrl(dto.getUrl());
        productSkuImage.setSort(dto.getSort());
        return productSkuImage;
    }

    public static List<ProductSkuImage> convertList(List<ProductSkuImageSaveDTO> list){
        List<ProductSkuImage> resList =new ArrayList<>();
        for (ProductSkuImageSaveDTO productImageSaveDTO : list) {
            resList.add(convert(productImageSaveDTO));
        }
        return resList;
    }

}
