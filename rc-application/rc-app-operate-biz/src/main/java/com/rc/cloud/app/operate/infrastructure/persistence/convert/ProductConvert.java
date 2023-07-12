package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.product.valobj.Remark;
import com.rc.cloud.app.operate.domain.model.product.valobj.Remark;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
        Remark remark = new Remark(po.getRemark());
        Tag tag = new Tag(po.getTag());
        BrandId brandId = new BrandId(po.getBrandId());
        CategoryName firstCategory = new CategoryName(po.getFirstCategory());
        CategoryName secondCategory = new CategoryName(po.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(po.getThirdCategory());
        CustomClassificationId customClassificationId = new CustomClassificationId(po.getCustomClassificationId());
        Newest newest = new Newest(po.getNewFlag());
        Explosives explosives = null;
        if(po.getExplosivesFlag()){
            explosives= new Explosives(po.getExplosivesFlag(), po.getExplosivesImage());
        }

        Recommend recommend = new Recommend(po.getRecommendFlag());
        Open open = new Open(po.getPublicFlag());
        OnshelfStatus onshelfStatus = new OnshelfStatus(po.getOnshelfStatus());
        Enable enable = new Enable(po.getEnabledFlag());
        Video video = new Video(po.getVideoUrl(), po.getVideoImg()
                , po.getInstallVideoUrl(), po.getInstallVideoImg());

        product.setRemark(remark);
        product.setTag(tag);
        product.setBrandId(brandId);
        product.setCategory(firstCategory,secondCategory,thirdCategory);
        product.setCustomClassificationId(customClassificationId);
        product.setNewest(newest);
        product.setExplosives(explosives);
        product.setRecommend(recommend);
        product.setOpen(open);
        product.setOnshelfStatus(onshelfStatus);
        product.setEnable(enable);
        product.setVideo(video);
        return product;

    }

    public static ProductPO convert(Product product){
         ProductPO target=new ProductPO();

        target.setId(product.getId().id());
        target.setTenantId(product.getTenantId().id());
        if(product.getEnable()!=null){
            target.setEnabledFlag(product.getEnable().result());
        }
        if(product.getName()!=null){
            target.setName(product.getName().getValue());
        }
        if(product.getRemark()!=null){
            target.setRemark(product.getRemark().getValue());
        }
        if(product.getTag()!=null){
            target.setTag(product.getTag().getValue());
        }
        if(product.getBrandId()!=null){
            target.setBrandId(product.getBrandId().id());
        }
        //类别
        if(product.getFirstCategory()!=null){
            target.setFirstCategory(product.getFirstCategory().getValue());
        }
        if(product.getSecondCategory()!=null){
            target.setSecondCategory(product.getSecondCategory().getValue());
        }
        if(product.getThirdCategory()!=null){
            target.setThirdCategory(product.getThirdCategory().getValue());
        }
        //自定义分类
        if(product.getCustomClassificationId()!=null){
            target.setCustomClassificationId(product.getCustomClassificationId().id());
        }
        if(product.getNewest()!=null){
            target.setNewFlag(product.getNewest().getValue());
        }
        if(product.getRecommend()!=null){
            target.setRecommendFlag(product.getRecommend().getValue());
        }
        if(product.getExplosives()!=null){
            target.setExplosivesFlag(product.getExplosives().isFlag());
            target.setExplosivesImage(product.getExplosives().getImage());
        }
        if(product.getOpen()!=null){
            target.setPublicFlag(product.getOpen().getValue());
        }
        //上架状态
        if(product.getOnshelfStatus()!=null){
            target.setOnshelfStatus(product.getOnshelfStatus().getValue());
        }
        //启用状态
        if(product.getEnable()!=null){
            target.setEnabledFlag(product.getEnable().result());
        }
        if(product.getVideo()!=null){
            target.setVideoImg(product.getVideo().getVideoImg());
            target.setVideoUrl(product.getVideo().getVideoUrl());
            target.setInstallVideoImg(product.getVideo().getInstallVideoImg());
            target.setInstallVideoUrl(product.getVideo().getInstallVideoUrl());
        }
        return target;
    }

    public static List<Product> convertList(List<ProductPO> list){
        List<Product> resList =new ArrayList<>();
        list.forEach(x->
                resList.add(convert(x))
                );
        return resList;
    }

}
