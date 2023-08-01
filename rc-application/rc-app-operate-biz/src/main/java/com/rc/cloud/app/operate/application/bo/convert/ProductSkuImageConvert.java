package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductSkuImageBO;
import com.rc.cloud.app.operate.application.dto.ProductSkuImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;

import java.util.ArrayList;
import java.util.List;

public class ProductSkuImageConvert {


    public static ProductSkuImage convertDomain(ProductSkuSaveDTO sku, ProductSkuImageSaveDTO skuImage){
        ProductSkuImage productSkuImage = new ProductSkuImage(new ProductSkuImageId(skuImage.getId()),new ProductSkuId(sku.getId())
                ,new Url(skuImage.getUrl())  ,new Sort(skuImage.getSort())
                );
        return productSkuImage;
    }

    public static List<ProductSkuImage> convertDomainList( ProductSkuSaveDTO sku,  List<ProductSkuImageSaveDTO> list){
        List<ProductSkuImage> resList =new ArrayList<>();
        if(list!=null){
            for (ProductSkuImageSaveDTO productImageSaveDTO : list) {
                resList.add(convertDomain(sku,productImageSaveDTO));
            }
        }
        return resList;
    }


    public static ProductSkuImageBO convertProductSkuImageBO(ProductSkuImage productSkuImage){
        ProductSkuImageBO bo =new ProductSkuImageBO();
        bo.setSort(productSkuImage.getSort().getValue());
        bo.setUrl(productSkuImage.getUrl().getValue());
        return bo;
    }

    public static List<ProductSkuImageBO> convertProductSkuImageBOList(List<ProductSkuImage> list){
        List<ProductSkuImageBO> resList =new ArrayList<>();
        for (ProductSkuImage productSkuImage : list) {
            resList.add(convertProductSkuImageBO(productSkuImage));
        }
        return resList;
    }



}
