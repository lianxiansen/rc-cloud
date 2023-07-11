package com.rc.cloud.app.operate.application.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductSkuBO {


    private String Id;

    private String tenantId;

    private String productId;

    private String skuCode;

    private BigDecimal supplyPrice;

    private BigDecimal weight;

    private String outId;

    private int limitBuy;

    private BigDecimal price;

    private int inventory;

    private int sort;

    private SeckillSkuBO seckillSku;

    private ProductSkuBO convert2ProductSkuBO(ProductSku productSku){

        ProductSkuBO productSkuBO=new ProductSkuBO();
        productSkuBO.setPrice(productSku.getPrice().getValue());

        if(productSku.getSeckillSku()!=null){
            SeckillSku seckillSku = productSku.getSeckillSku();
            //秒杀信息
            SeckillSkuBO seckillSkuBO=new SeckillSkuBO();
            seckillSkuBO.setSeckillInventory(seckillSku.getSeckillInventory().getValue());
            seckillSkuBO.setSeckillPrice(seckillSku.getSeckillPrice().getValue());
            seckillSkuBO.setSeckillTotalInventory(seckillSku.getSeckillTotalInventory().getValue());
            seckillSkuBO.setSeckillLimitBuy(seckillSku.getSeckillLimitBuy().getValue());
            productSkuBO.setSeckillSku(seckillSkuBO);
        }
        if(productSku.getInventory()!=null){
            productSkuBO.setInventory(productSku.getInventory().getValue());
        }
        if(productSku.getLimitBuy()!=null){
            productSkuBO.setLimitBuy(productSku.getLimitBuy().getValue());
        }
        if(productSku.getSupplyPrice()!=null){
            productSkuBO.setSupplyPrice(productSku.getSupplyPrice().getValue());
        }
        if(productSku.getWeight()!=null){
            productSkuBO.setWeight(productSku.getWeight().getValue());
        }
        if(productSku.getOutId()!=null){
            productSkuBO.setOutId(productSku.getOutId().getValue());
        }
        if(productSku.getSort()!=null){
            productSkuBO.setSort(productSku.getSort().getValue());
        }
        return productSkuBO;
    }


}
