package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.product.valobj.Remark;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductPO;
import com.rc.cloud.common.core.util.StringUtils;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class ProductConvert {

    public static Product convertDomain(ProductPO po){
        if(po==null){
            return null;
        }
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
        //SpuCode
        SpuCode spuCode=new SpuCode(po.getSpuCode());
        product.setSpuCode(spuCode);
        //OutId
        OutId outId=new OutId(po.getOutId());
        product.setOutid(outId);
        //Origin
        if(po.getProductOrigin()!=null){
            product.setOrigin(new Origin(po.getProductOrigin()));
        }
        //ProductType
        if(po.getProductType()!=null){
            product.setType(new Type(po.getProductType()));
        }
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
            explosives= new Explosives(po.getExplosivesFlag(), new Url(po.getExplosivesImage()));
        }
        product.setExplosives(explosives);
        //Recommend
        if(po.getRecommendFlag()!=null){
            Recommend recommend = new Recommend(po.getRecommendFlag());
            product.setRecommendFlag(recommend);
        }

        //OnshelfStatus
        if(po.getOnshelfStatus()!=null){
            OnshelfStatus onshelfStatus = new OnshelfStatus(po.getOnshelfStatus());
            product.setOnshelfStatus(onshelfStatus);
        }
        //Video
        Video video = new Video(new Url(po.getVideoUrl()));
        if(StringUtils.isNotEmpty(po.getVideoImg())){
            video.setVideoImg(new Url(po.getVideoImg()));
        }
        product.setVideo(video);
        //NewFlag
        if(po.getNewFlag()!=null){
            product.setNewFlag(po.getNewFlag());
        }
        //PublicFlag
        if(po.getPublicFlag()!=null){
            product.setPublicFlag(po.getPublicFlag());
        }
        //PackingLowestBuyFlag
        if(po.getPackingLowestBuyFlag()!=null){
            product.setPackingLowestBuy(new PackingLowestBuy(po.getPackingLowestBuyFlag()));
        }
        return product;

    }

    public static ProductPO convertProductPO(Product product){
        ProductPO po=new ProductPO();
        po.setId(product.getId().id());
        po.setTenantId(product.getTenantId().id());
        if(product.getName()!=null){
            po.setName(product.getName().getValue());
        }
        if(product.getOrigin()!=null){
            po.setProductOrigin(product.getOrigin().getValue());
        }
        if(product.getType()!=null){
            po.setProductType(product.getType().getValue());
        }
        if(product.getSpuCode()!=null){
            po.setSpuCode(product.getSpuCode().getValue());
        }
        if(product.getOutid()!=null){
            po.setOutId(product.getOutid().getValue());
        }
        if(product.getSort()!=null){
            po.setSort(product.getSort().getValue());
        }
        if(product.getPackingLowestBuy()!=null){
            po.setPackingLowestBuyFlag(product.getPackingLowestBuy().result());
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
        if(product.getRecommendFlag()!=null){
            po.setRecommendFlag(product.getRecommendFlag().getValue());
        }
        if(product.getExplosives()!=null){
            po.setExplosivesFlag(product.getExplosives().isFlag());
            po.setExplosivesImage(product.getExplosives().getImage().getValue());
        }
        if(product.getPublicFlag()!=null){
            po.setPublicFlag(product.getPublicFlag());
        }
        //上架状态
        if(product.getOnshelfStatus()!=null){
            po.setOnshelfStatus(product.getOnshelfStatus().getValue());
        }
        //视频
        if(product.getVideo()!=null){
            if(product.getVideo().getVideoImg()!=null){
                po.setVideoImg(product.getVideo().getVideoImg().getValue());
            }
            po.setVideoUrl(product.getVideo().getVideoUrl().getValue());
        }
        return po;
    }

    public static List<Product> convertDomainList(List<ProductPO> list){
        List<Product> resList =new ArrayList<>();
        list.forEach(x->
                resList.add(convertDomain(x))
                );
        return resList;
    }

}
