package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import cn.hutool.json.JSONUtil;
import com.rc.cloud.app.operate.application.dto.ProductAttributeSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductDictSaveDTO;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttributeEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductDictEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductImageEntity;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.SeckillSku;
import com.rc.cloud.app.operate.infrastructure.persistence.po.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class ProductDOConvert {

    public static ProductDO convert2ProductDO(Product product) {
        ProductDO target=new ProductDO();

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

    public static  ProductDetailDO convert2ProductDetailDO(String productId, String tenantId, String detail){
        ProductDetailDO productDetailDO=new ProductDetailDO();
        productDetailDO.setDetail(detail);
        productDetailDO.setProductId(productId);
        productDetailDO.setTenantId(tenantId);
        return productDetailDO;
    }

    public static ProductAttributeDO convert2ProductAttributeDO(String productId, String tenantId, SortedSet<ProductAttributeEntity> attributes){
        ProductAttributeDO productAttributeDO=new ProductAttributeDO();
        //不一定正确 TODO
        String attr = JSONUtil.toJsonStr(attributes);
        productAttributeDO.setProductId(productId);
        productAttributeDO.setTenantId(tenantId);
        productAttributeDO.setContent(attr);
        return productAttributeDO;
    }


    public static List<ProductImageDO> convert2ProductImageDO(String productId, String tenantId, List<ProductImageEntity> productImageEntityList){
        List<ProductImageDO> resList =new ArrayList<>();
        if(productImageEntityList!=null){
            for (ProductImageEntity productImageEntity : productImageEntityList) {
                ProductImageDO productImageDO=new ProductImageDO();
                productImageDO.setId(productImageEntity.getId());
                productImageDO.setProductId(productId);
                productImageDO.setUrl(productImageEntity.getUrl());
                productImageDO.setTenantId(tenantId);
                productImageDO.setSortId(productImageEntity.getSort());
                resList.add(productImageDO);
            }
        }

        return resList;
    }


    public static List<ProductDictDO> convert2ProductDictDO(String productId, String tenantId, List<ProductDictEntity> productDictEntityList){
        List<ProductDictDO> resList =new ArrayList<>();
        if(productDictEntityList!=null){
            for (ProductDictEntity productDictEntity : productDictEntityList) {
                ProductDictDO productDictDO=new ProductDictDO();
                productDictDO.setId(productDictEntity.getId());
                productDictDO.setProductId(productId);
                productDictDO.setValue(productDictEntity.getValue());
                productDictDO.setTenantId(tenantId);
                productDictDO.setKey(productDictEntity.getKey());
                productDictDO.setSortId(productDictEntity.getSort());
                resList.add(productDictDO);
            }
        }
        return resList;
    }
}
