package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuAttributePO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuImagePO;

import java.util.ArrayList;
import java.util.List;

public class ProductSkuDOConvert {


    public static ProductSkuPO convert2ProductSkuDO(ProductSku productSku) {
        ProductSkuPO target=new ProductSkuPO();

        target.setId(productSku.getId().id());
        target.setProductId(productSku.getProductId().id());
        target.setTenantId(productSku.getTenantId().id());
        if(productSku.getSkuCode()!=null){
            target.setSkuCode(productSku.getSkuCode());
        }
        if(productSku.getPrice()!=null){
            target.setPrice(productSku.getPrice().getValue());
        }
        if(productSku.getInventory()!=null){
            target.setInventory(productSku.getInventory().getValue());
        }
        if(productSku.getLimitBuy()!=null){
            target.setLimitBuy(productSku.getLimitBuy().getValue());
        }
        if(productSku.getOutId()!=null){
            target.setOutId(productSku.getOutId().getValue());
        }
        if(productSku.getSupplyPrice()!=null){
            target.setSupplyPrice(productSku.getSupplyPrice().getValue());
        }
        if(productSku.getWeight()!=null){
            target.setWeight(productSku.getWeight().getValue());
        }
        if(productSku.getSeckillSku()!=null){
            target.setSeckillInventory(productSku.getSeckillSku().getSeckillInventory().getValue());
            target.setSeckillPrice(productSku.getSeckillSku().getSeckillPrice().getValue());
            target.setSeckillLimitBuy(productSku.getSeckillSku().getSeckillLimitBuy().getValue());
            target.setSeckillTotalInventory(productSku.getSeckillSku().getSeckillTotalInventory().getValue());
        }
        return target;
    }


    public static ProductSkuAttributePO convert2ProductSkuAttributeDO(
            String productSkuId, String tenantId, ProductSkuAttribute productSkuAttributeEntity){
        ProductSkuAttributePO productSkuAttributePO =new ProductSkuAttributePO();
        String attr = JSON.toJSONString(productSkuAttributeEntity.getSkuAttributes());
        productSkuAttributePO.setId(productSkuAttributeEntity.getId().id());
        productSkuAttributePO.setProductSkuId(productSkuId);
        productSkuAttributePO.setTenantId(tenantId);
        productSkuAttributePO.setContent(attr);
        return productSkuAttributePO;
    }


    public static List<ProductSkuImagePO> convert2ProductSkuImageDO(String productSkuId, String tenantId, List<ProductSkuImage> ProductSkuImageEntityList){
        List<ProductSkuImagePO> resList =new ArrayList<>();
        if(ProductSkuImageEntityList!=null){
            for (ProductSkuImage productSkuImageEntity : ProductSkuImageEntityList) {
                ProductSkuImagePO productSkuImagePO =new ProductSkuImagePO();
                productSkuImagePO.setId(productSkuImageEntity.getId().id());
                productSkuImagePO.setProductSkuId(productSkuId);
                productSkuImagePO.setUrl(productSkuImageEntity.getUrl());
                productSkuImagePO.setTenantId(tenantId);
                productSkuImagePO.setSortId(productSkuImageEntity.getSort());
                resList.add(productSkuImagePO);
            }
        }

        return resList;
    }




}
