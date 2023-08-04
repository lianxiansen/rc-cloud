package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductImageBO;
import com.rc.cloud.app.operate.application.dto.ProductImageSaveDTO;
import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImageId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.ArrayList;
import java.util.List;

public class ProductImageConvert {

    public static ProductImage convertDomain(ProductImageSaveDTO dto
              ,ProductId productId
            ,ProductImageTypeEnum type){
        ProductImage productImage = new ProductImage(new ProductImageId(dto.getId()),productId,new Url(dto.getUrl()),new Sort(dto.getSort()),
                type);
        return productImage;
    }


    public static List<ProductImage> convertDomainList(List<ProductImageSaveDTO> list,
            ProductId productId,ProductImageTypeEnum type){
        if(list==null){
            return null;
        }
        List<ProductImage> resList =new ArrayList<>();
        if(list!=null){
            for (ProductImageSaveDTO dto : list) {
                resList.add(convertDomain(dto,productId, type));
            }
        }
        return resList;
    }



    public static ProductImageBO convertProductImageBO(ProductImage productImage){
        ProductImageBO bo =new ProductImageBO();
        bo.setId(productImage.getId().getId());
        bo.setSort(productImage.getSort().getValue());
        bo.setUrl(productImage.getUrl().getValue());
        bo.setType(productImage.getType());
        return bo;
    }

    public static List<ProductImageBO> convertProductImageBOList(List<ProductImage> list){
        List<ProductImageBO> resList =new ArrayList<>();
        if(list!=null){
            for (ProductImage productImage : list) {
                resList.add(convertProductImageBO(productImage));
            }
        }

        return resList;
    }


}
