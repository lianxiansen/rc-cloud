package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.SeckillSku;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;

public class ProductSkuDOConvert {


    public static ProductSkuDO convert2ProductSkuDO(ProductSku source) {
        ProductSkuDO target=new ProductSkuDO();

        target.setId(source.getId().id());
        target.setProductId(source.getProductId().id());
        target.setSkuCode(source.getSkuCode());
        target.setPrice(source.getPrice().getValue());
        target.setInventory(source.getInventory().getValue());
        target.setLimitBuy(source.getLimitBuy().getValue());
        target.setOutId(source.getOutId().getValue());
        target.setHasImageFlag(source.isHasImageFlag());
        target.setSupplyPrice(source.getSupplyPrice().getValue());
        target.setWeight(source.getWeight().getValue());
        SeckillSku seckillSku = source.getSeckillSku();
        if(seckillSku!=null){
            target.setSeckillInventory(seckillSku.getSeckillInventory().getValue());
            target.setSeckillPrice(seckillSku.getSeckillPrice().getValue());
            target.setSeckillLimitBuy(seckillSku.getSeckillLimitBuy().getValue());
            target.setSeckillTotalInventory(seckillSku.getSeckillTotalInventory().getValue());
        }
        return target;
    }

}
