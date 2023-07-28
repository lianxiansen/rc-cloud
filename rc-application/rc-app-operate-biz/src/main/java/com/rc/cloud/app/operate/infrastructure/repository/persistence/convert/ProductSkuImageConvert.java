package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuImagePO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuImagePO;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class ProductSkuImageConvert {



    public static ProductSkuImage convert(ProductSkuImagePO po){
        if(po!=null){
            ProductSkuImage productSkuImage=new ProductSkuImage(new ProductSkuImageId(po.getId()));
            productSkuImage.setSort(po.getSort());
            productSkuImage.setUrl(po.getUrl());
            return productSkuImage;
        }
        return null;
    }

    public static ProductSkuImagePO convert(ProductSkuImage productSkuImage){
        ProductSkuImagePO po =new ProductSkuImagePO();
        po.setId(productSkuImage.getId().id());
        po.setUrl(productSkuImage.getUrl());
        po.setSort(productSkuImage.getSort());
        return po;
    }

    public static List<ProductSkuImage> convertList(List<ProductSkuImagePO> list){
        List<ProductSkuImage> resList =new ArrayList<>();
        if(list!=null){
            list.forEach(x-> resList.add(convert(x)));
        }
        return resList;
    }
}
