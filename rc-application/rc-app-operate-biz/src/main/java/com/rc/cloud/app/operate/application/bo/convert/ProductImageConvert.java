package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.dto.ProductImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;

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
        for (ProductImageSaveDTO productImageSaveDTO : list) {
            resList.add(convert(productImageSaveDTO));
        }
        return resList;
    }

}
