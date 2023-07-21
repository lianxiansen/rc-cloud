package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.product.valobj.Remark;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductPO;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class ProductConvert {


    ProductConvert INSTANCE = Mappers.getMapper(ProductConvert.class);

    public static Product convert(ProductPO po){
        ProductId productId=new ProductId(po.getId());
        TenantId tenantId = new TenantId(po.getTenantId());
        Product product=new Product(productId,tenantId,new Name(po.getName()));
        product.setId(productId);
        //Remark
        Remark remark = new Remark(po.getRemark());
        product.setRemark(remark);
        //ListImage
        Url url =new Url(po.getListImage());
        product.setProductListImage(url);
        //Tag
        Tag tag = new Tag(po.getTag());
        product.setTag(tag);
        //BrandId
        BrandId brandId = new BrandId(po.getBrandId());
        product.setBrandId(brandId);
        //CategoryName
        CategoryName firstCategory = new CategoryName(po.getFirstCategory());
        CategoryName secondCategory = new CategoryName(po.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(po.getThirdCategory());
        product.setCategory(firstCategory,secondCategory,thirdCategory);
        //CustomClassificationId
        CustomClassificationId customClassificationId = new CustomClassificationId(po.getCustomClassificationId());
        product.setCustomClassificationId(customClassificationId);
        //Explosives
        Explosives explosives = null;
        if(po.getExplosivesFlag()){
            explosives= new Explosives(po.getExplosivesFlag(), po.getExplosivesImage());
        }
        product.setExplosives(explosives);
        //Recommend
        Recommend recommend = new Recommend(po.getRecommendFlag());
        product.setRecommend(recommend);
        //OnshelfStatus
        OnshelfStatus onshelfStatus = new OnshelfStatus(po.getOnshelfStatus());
        product.setOnshelfStatus(onshelfStatus);
        //Video
        Video video = new Video(po.getVideoUrl(), po.getVideoImg());
        product.setVideo(video);
        //InstallInformation
        InstallInformation installInformation =new InstallInformation();
        installInformation.setInstallVideoImg(po.getInstallVideoImg());
        installInformation.setInstallVideoUrl(po.getInstallVideoImg());
        installInformation.setInstallDetail(po.getInstallDetail());
        product.setInstallInformation(installInformation);
        //NewFlag
        product.setNewFlag(po.getNewFlag());
        //PublicFlag
        product.setPublicFlag(po.getPublicFlag());
        return product;

    }

    public static ProductPO convert(Product product){
         ProductPO po=new ProductPO();
        po.setId(product.getId().id());
        po.setTenantId(product.getTenantId().id());
        if(product.getName()!=null){
            po.setName(product.getName().getValue());
        }
        if(product.getProductListImage()!=null){
            po.setListImage(product.getProductListImage().getValue());
        }
        if(product.getRemark()!=null){
            po.setRemark(product.getRemark().getValue());
        }
        if(product.getTag()!=null){
            po.setTag(product.getTag().getValue());
        }
        if(product.getBrandId()!=null){
            po.setBrandId(product.getBrandId().id());
        }
        //类别
        if(product.getFirstCategory()!=null){
            po.setFirstCategory(product.getFirstCategory().getValue());
        }
        if(product.getSecondCategory()!=null){
            po.setSecondCategory(product.getSecondCategory().getValue());
        }
        if(product.getThirdCategory()!=null){
            po.setThirdCategory(product.getThirdCategory().getValue());
        }
        //自定义分类
        if(product.getCustomClassificationId()!=null){
            po.setCustomClassificationId(product.getCustomClassificationId().id());
        }
        if(product.getNewFlag()!=null){
            po.setNewFlag(product.getNewFlag());
        }
        if(product.getRecommend()!=null){
            po.setRecommendFlag(product.getRecommend().getValue());
        }
        if(product.getExplosives()!=null){
            po.setExplosivesFlag(product.getExplosives().isFlag());
            po.setExplosivesImage(product.getExplosives().getImage());
        }
        if(product.getPublicFlag()!=null){
            po.setPublicFlag(product.getPublicFlag());
        }
        //上架状态
        if(product.getOnshelfStatus()!=null){
            po.setOnshelfStatus(product.getOnshelfStatus().getValue());
        }
        if(product.getVideo()!=null){
            po.setVideoImg(product.getVideo().getVideoImg());
            po.setVideoUrl(product.getVideo().getVideoUrl());
        }
        if(product.getInstallInformation()!=null){
            po.setInstallVideoUrl(product.getInstallInformation().getInstallVideoUrl());
            po.setInstallVideoImg(product.getInstallInformation().getInstallVideoImg());
            po.setInstallDetail(product.getInstallInformation().getInstallDetail());

        }
        return po;
    }

    public static List<Product> convertList(List<ProductPO> list){
        List<Product> resList =new ArrayList<>();
        list.forEach(x->
                resList.add(convert(x))
                );
        return resList;
    }

}
