package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductImagePO;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public class ProductImageConvert {



    public static ProductImage convert(ProductImagePO productImagePO){
       if(productImagePO!=null){
           ProductImageTypeEnum productImageTypeEnum= ProductImageTypeEnum.MasterImage;
           Integer imageType = productImagePO.getImageType();
           if(imageType==ProductImageTypeEnum.MasterImage.value){
               productImageTypeEnum= ProductImageTypeEnum.MasterImage;
           }else if(imageType==ProductImageTypeEnum.SizeImage.value){
               productImageTypeEnum= ProductImageTypeEnum.SizeImage;
           }
           ProductImage productImage=new ProductImage(
                   new ProductId(productImagePO.getProductId()),
                   new TenantId(productImagePO.getTenantId()),
                   new Url(productImagePO.getUrl()),
                   new Sort(productImagePO.getSort()),
                   productImageTypeEnum
           );
           return  productImage;
       }
       return null;
    }

    /**
     * ProductImage转ProductImagePO
     * 不需要指定ProductImagePO的id
     * @param productImage
     * @return
     */
    public static ProductImagePO convert(ProductImage productImage){
        ProductImagePO po =new ProductImagePO();
        po.setUrl(productImage.getUrl().getValue());
        po.setSort(productImage.getSort().getValue());
        po.setTenantId(productImage.getTenantId().id());
        po.setProductId(productImage.getProductId().id());
        po.setImageType(productImage.getType().value);
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
