package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductImagePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public class ProductImageConvert {



    public static ProductImage convert(ProductImagePO productImagePO){
       if(productImagePO!=null){
           ProductImageTypeEnum productImageTypeEnum= ProductImageTypeEnum.MasterImage;
           Integer imageType = productImagePO.getImage_type();
           if(imageType==ProductImageTypeEnum.MasterImage.value){
               productImageTypeEnum= ProductImageTypeEnum.MasterImage;
           }else if(imageType==ProductImageTypeEnum.SizeImage.value){
               productImageTypeEnum= ProductImageTypeEnum.SizeImage;
           }
           ProductImage productImage=new ProductImage(
                   new ProductImageId(productImagePO.getId()),
                   new Url(productImagePO.getUrl()),
                   new Sort(productImagePO.getSort()),
                   productImageTypeEnum
           );
           return  productImage;
       }
       return null;
    }


    public static ProductImagePO convert(ProductImage productImage){
        ProductImagePO po =new ProductImagePO();
        po.setId(productImage.getId().id());
        po.setUrl(productImage.getUrl().getValue());
        po.setSort(productImage.getSort().getValue());
        po.setImage_type(productImage.getType().value);
        return po;
    }

    public static List<ProductImage> convertList(List<ProductImagePO> list){
        List<ProductImage> resList =new ArrayList<>();
        if(list!=null){
            list.forEach(x-> resList.add(convert(x)));
        }
        return resList;
    }
}
