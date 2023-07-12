package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttributeEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductDictEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductImageEntity;
import com.rc.cloud.app.operate.infrastructure.persistence.po.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDOConvert {

    public static ProductPO convert2ProductDO(Product product) {
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

    public static ProductDetailPO convert2ProductDetailDO(String detailId, String productId, String tenantId, String detail){
        ProductDetailPO productDetailPO =new ProductDetailPO();
        productDetailPO.setId(detailId);
        productDetailPO.setDetail(detail);
        productDetailPO.setProductId(productId);
        productDetailPO.setTenantId(tenantId);
        return productDetailPO;
    }

    public static ProductAttributePO convert2ProductAttributeDO(String productId, String tenantId, ProductAttributeEntity productAttributeEntity){
        ProductAttributePO productAttributePO =new ProductAttributePO();
        String attr = JSON.toJSONString(productAttributeEntity.getAttributes());
        productAttributePO.setId(productAttributeEntity.getId());
        productAttributePO.setProductId(productId);
        productAttributePO.setTenantId(tenantId);
        productAttributePO.setContent(attr);
        return productAttributePO;
    }


    public static List<ProductImagePO> convert2ProductImageDO(String productId, String tenantId, List<ProductImageEntity> productImageEntityList){
        List<ProductImagePO> resList =new ArrayList<>();
        if(productImageEntityList!=null){
            for (ProductImageEntity productImageEntity : productImageEntityList) {
                ProductImagePO productImagePO =new ProductImagePO();
                productImagePO.setId(productImageEntity.getId());
                productImagePO.setProductId(productId);
                productImagePO.setUrl(productImageEntity.getUrl());
                productImagePO.setTenantId(tenantId);
                productImagePO.setSortId(productImageEntity.getSort());
                resList.add(productImagePO);
            }
        }

        return resList;
    }


    public static List<ProductDictPO> convert2ProductDictDO(String productId, String tenantId, List<ProductDictEntity> productDictEntityList){
        List<ProductDictPO> resList =new ArrayList<>();
        if(productDictEntityList!=null){
            for (ProductDictEntity productDictEntity : productDictEntityList) {
                ProductDictPO productDictPO =new ProductDictPO();
                productDictPO.setId(productDictEntity.getId());
                productDictPO.setProductId(productId);
                productDictPO.setValue(productDictEntity.getValue());
                productDictPO.setTenantId(tenantId);
                productDictPO.setKey(productDictEntity.getKey());
                productDictPO.setSortId(productDictEntity.getSort());
                resList.add(productDictPO);
            }
        }
        return resList;
    }
}
