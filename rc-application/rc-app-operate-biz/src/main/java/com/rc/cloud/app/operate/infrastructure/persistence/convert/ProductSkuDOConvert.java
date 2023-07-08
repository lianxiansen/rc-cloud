package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttributeEntity;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImageEntity;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.SeckillSku;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributeDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImageDO;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class ProductSkuDOConvert {


    public static ProductSkuDO convert2ProductSkuDO(ProductSku productSku) {
        ProductSkuDO target=new ProductSkuDO();

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


    public static ProductSkuAttributeDO convert2ProductSkuAttributeDO(
            String productSkuId, String tenantId, ProductSkuAttributeEntity productSkuAttributeEntity){
        ProductSkuAttributeDO productSkuAttributeDO=new ProductSkuAttributeDO();
        String attr = JSON.toJSONString(productSkuAttributeEntity.getSkuAttributes());
        productSkuAttributeDO.setId(productSkuAttributeEntity.getId());
        productSkuAttributeDO.setProductSkuId(productSkuId);
        productSkuAttributeDO.setTenantId(tenantId);
        productSkuAttributeDO.setContent(attr);
        return productSkuAttributeDO;
    }


    public static List<ProductSkuImageDO> convert2ProductSkuImageDO(String productSkuId, String tenantId, List<ProductSkuImageEntity> ProductSkuImageEntityList){
        List<ProductSkuImageDO> resList =new ArrayList<>();
        if(ProductSkuImageEntityList!=null){
            for (ProductSkuImageEntity productSkuImageEntity : ProductSkuImageEntityList) {
                ProductSkuImageDO productSkuImageDO=new ProductSkuImageDO();
                productSkuImageDO.setId(productSkuImageEntity.getId());
                productSkuImageDO.setProductSkuId(productSkuId);
                productSkuImageDO.setUrl(productSkuImageEntity.getUrl());
                productSkuImageDO.setTenantId(tenantId);
                productSkuImageDO.setSortId(productSkuImageEntity.getSort());
                resList.add(productSkuImageDO);
            }
        }

        return resList;
    }




}
