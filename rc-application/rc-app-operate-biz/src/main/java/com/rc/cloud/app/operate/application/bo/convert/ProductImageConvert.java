package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductImageBO;
import com.rc.cloud.app.operate.application.dto.ProductImageSaveDTO;
import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;

import java.util.ArrayList;
import java.util.List;

public class ProductImageConvert {

    public static ProductImage convert(ProductImageSaveDTO albums ,ProductImageTypeEnum type){
        ProductImage productImage = new ProductImage(new ProductImageId(albums.getId())
        ,new Url(albums.getUrl())
        ,new Sort(albums.getSort())
        , type);
        return productImage;
    }


    public static List<ProductImage> convertList(List<ProductImageSaveDTO> list,ProductImageTypeEnum type){
        if(list==null){
            return null;
        }
        List<ProductImage> resList =new ArrayList<>();
        if(list!=null){
            for (ProductImageSaveDTO dto : list) {
                resList.add(convert(dto,type));
            }
        }
        return resList;
    }



    public static ProductImageBO convertProductImageBO(ProductImage productImage){
        ProductImageBO bo =new ProductImageBO();
        bo.setSort(productImage.getSort().getValue());
        bo.setUrl(productImage.getUrl().getValue());
        bo.setType(productImage.getType());
        return bo;
    }

    public static List<ProductImageBO> convertProductImageBOList(List<ProductImage> list){
        List<ProductImageBO> resList =new ArrayList<>();
        for (ProductImage productImage : list) {
            resList.add(convertProductImageBO(productImage));
        }
        return resList;
    }


}
