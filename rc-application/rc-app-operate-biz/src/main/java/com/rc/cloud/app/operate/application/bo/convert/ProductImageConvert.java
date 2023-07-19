package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductImageBO;
import com.rc.cloud.app.operate.application.dto.ProductImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;

import java.util.ArrayList;
import java.util.List;

public class ProductImageConvert {

    public static ProductImage convert(ProductImageSaveDTO productImageSaveDTO){
        ProductImage productImage = new ProductImage(new ProductImageId(productImageSaveDTO.getId()));
        productImage.setUrl(productImageSaveDTO.getUrl());
        productImage.setSort(productImageSaveDTO.getSort());
        return productImage;
    }

    public static List<ProductImage> convertList(List<ProductImageSaveDTO> list){
        List<ProductImage> resList =new ArrayList<>();
        if(list!=null){
            for (ProductImageSaveDTO productImageSaveDTO : list) {
                resList.add(convert(productImageSaveDTO));
            }
        }

        return resList;
    }

    public static ProductImageBO convert(ProductImage productImage){
        ProductImageBO bo =new ProductImageBO();
        bo.setSort(productImage.getSort());
        bo.setUrl(productImage.getUrl());
        return bo;
    }

    public static List<ProductImageBO> convertProductImageBOList(List<ProductImage> list){
        List<ProductImageBO> resList =new ArrayList<>();
        for (ProductImage productImage : list) {
            resList.add(convert(productImage));
        }
        return resList;
    }

}
