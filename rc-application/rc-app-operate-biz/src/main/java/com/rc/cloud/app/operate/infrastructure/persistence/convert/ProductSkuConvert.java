package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuPO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuPO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImagePO;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public interface ProductSkuConvert {


//    private ProductSku convert2ProductSkuEntity(ProductSkuPO productSkuPO){
//        ProductId productId=new ProductId(productSkuPO.getProductId());
//        ProductSkuId id = nextId();
//        TenantId tenantId = new TenantId(productSkuPO.getTenantId());
//        Price price=new Price();
//        price.setValue(productSkuPO.getPrice());
//        ProductSku productSku=new ProductSku(id,productId,tenantId, price);
//        //秒杀信息
//        SeckillSku seckillSku=new SeckillSku();
//        seckillSku.setSeckillInventory(new Inventory(productSkuPO.getSeckillInventory()));
//        seckillSku.setSeckillPrice(new Price(productSkuPO.getPrice()));
//        seckillSku.setSeckillTotalInventory(new TotalInventory(productSkuPO.getSeckillTotalInventory()));
//        seckillSku.setSeckillLimitBuy(new LimitBuy(productSkuPO.getSeckillLimitBuy()));
//        productSku.setSeckillSku(seckillSku);
//        productSku.setInventory(new Inventory(productSkuPO.getInventory()));
//        productSku.setLimitBuy(new LimitBuy(productSkuPO.getLimitBuy()));
//        productSku.setOutId(new OutId(productSkuPO.getOutId()));
//        productSku.setSupplyPrice(new SupplyPrice(productSkuPO.getSupplyPrice()));
//        productSku.setWeight(new Weight(productSkuPO.getWeight()));
//        return productSku;
//    }
    
    ProductSkuConvert INSTANCE = Mappers.getMapper(ProductSkuConvert.class);

    ProductSku convert(ProductSkuPO productPO);

    ProductSkuPO convert(ProductSku product);

    List<ProductSku> convertList(List<ProductSkuPO> list);

//    public static ProductSkuPO convert2ProductSkuDO(ProductSku productSku) {
//        ProductSkuPO target=new ProductSkuPO();
//
//        target.setId(productSku.getId().id());
//        target.setProductId(productSku.getProductId().id());
//        target.setTenantId(productSku.getTenantId().id());
//        if(productSku.getSkuCode()!=null){
//            target.setSkuCode(productSku.getSkuCode());
//        }
//        if(productSku.getPrice()!=null){
//            target.setPrice(productSku.getPrice().getValue());
//        }
//        if(productSku.getInventory()!=null){
//            target.setInventory(productSku.getInventory().getValue());
//        }
//        if(productSku.getLimitBuy()!=null){
//            target.setLimitBuy(productSku.getLimitBuy().getValue());
//        }
//        if(productSku.getOutId()!=null){
//            target.setOutId(productSku.getOutId().getValue());
//        }
//        if(productSku.getSupplyPrice()!=null){
//            target.setSupplyPrice(productSku.getSupplyPrice().getValue());
//        }
//        if(productSku.getWeight()!=null){
//            target.setWeight(productSku.getWeight().getValue());
//        }
//        if(productSku.getSeckillSku()!=null){
//            target.setSeckillInventory(productSku.getSeckillSku().getSeckillInventory().getValue());
//            target.setSeckillPrice(productSku.getSeckillSku().getSeckillPrice().getValue());
//            target.setSeckillLimitBuy(productSku.getSeckillSku().getSeckillLimitBuy().getValue());
//            target.setSeckillTotalInventory(productSku.getSeckillSku().getSeckillTotalInventory().getValue());
//        }
//        return target;
//    }
//
//
//    public static ProductSkuPO convert2ProductSkuDO(
//            String productSkuId, String tenantId, ProductSku productSku){
//        ProductSkuPO productSkuPO =new ProductSkuPO();
//        String attr = JSON.toJSONString(productSku.getSkus());
//        productSkuPO.setId(productSku.getId());
//        productSkuPO.setProductSkuId(productSkuId);
//        productSkuPO.setTenantId(tenantId);
//        productSkuPO.setContent(attr);
//        return productSkuPO;
//    }
//
//
//    public static List<ProductSkuImagePO> convert2ProductSkuImageDO(String productSkuId, String tenantId, List<ProductSkuImage> productSkuImageList){
//        List<ProductSkuImagePO> resList =new ArrayList<>();
//        if(productSkuImageList !=null){
//            for (ProductSkuImage productSkuImage : productSkuImageList) {
//                ProductSkuImagePO productSkuImagePO =new ProductSkuImagePO();
//                productSkuImagePO.setId(productSkuImage.getId());
//                productSkuImagePO.setProductSkuId(productSkuId);
//                productSkuImagePO.setUrl(productSkuImage.getUrl());
//                productSkuImagePO.setTenantId(tenantId);
//                productSkuImagePO.setSortId(productSkuImage.getSort());
//                resList.add(productSkuImagePO);
//            }
//        }
//
//        return resList;
//    }
//



}
