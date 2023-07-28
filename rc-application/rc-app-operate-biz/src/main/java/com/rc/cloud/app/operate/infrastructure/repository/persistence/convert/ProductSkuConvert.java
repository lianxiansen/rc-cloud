package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuPO;

import java.util.ArrayList;
import java.util.List;

public class ProductSkuConvert {


    public static ProductSku convert(ProductSkuPO po){
        if(po!=null){
            ProductId productId=new ProductId(po.getProductId());
            ProductSkuId id = new ProductSkuId(po.getId());
            TenantId tenantId = new TenantId(po.getTenantId());
            Price price=new Price();
            price.setValue(po.getPrice());
            ProductSku productSku=new ProductSku(id,productId,tenantId);
            //秒杀信息
            SeckillSku seckillSku=new SeckillSku();
            seckillSku.setSeckillInventory(new Inventory(po.getSeckillInventory()));
            seckillSku.setSeckillPrice(new Price(po.getPrice()));
            seckillSku.setSeckillTotalInventory(new TotalInventory(po.getSeckillTotalInventory()));
            seckillSku.setSeckillLimitBuy(new LimitBuy(po.getSeckillLimitBuy()));
            productSku.setSeckillSku(seckillSku);
            productSku.setInventory(new Inventory(po.getInventory()));
            productSku.setLimitBuy(new LimitBuy(po.getLimitBuy()));
            productSku.setOutId(new OutId(po.getOutId()));
            productSku.setSupplyPrice(new SupplyPrice(po.getSupplyPrice()));
            productSku.setWeight(new Weight(po.getWeight()));
            productSku.setPackingNumber(new PackingNumber(po.getPackingNumber()));
            productSku.setCartonSize(new CartonSize(po.getCartonSizeLength(),po.getCartonSizeWidth()
                    ,po.getCartonSizeHeight()));
            return productSku;
        }
        return null;
    }

    public static ProductSkuPO convert(ProductSku productSku){
        ProductSkuPO po=new ProductSkuPO();
        po.setId(productSku.getId().id());
        po.setProductId(productSku.getProductId().id());
        po.setTenantId(productSku.getTenantId().id());
        if(productSku.getSkuCode()!=null){
            po.setSkuCode(productSku.getSkuCode());
        }
        if(productSku.getPrice()!=null){
            po.setPrice(productSku.getPrice().getValue());
        }
        if(productSku.getInventory()!=null){
            po.setInventory(productSku.getInventory().getValue());
        }
        if(productSku.getLimitBuy()!=null){
            po.setLimitBuy(productSku.getLimitBuy().getValue());
        }
        if(productSku.getOutId()!=null){
            po.setOutId(productSku.getOutId().getValue());
        }
        if(productSku.getSupplyPrice()!=null){
            po.setSupplyPrice(productSku.getSupplyPrice().getValue());
        }
        if(productSku.getWeight()!=null){
            po.setWeight(productSku.getWeight().getValue());
        }
        if(productSku.getSeckillSku()!=null){
            po.setSeckillInventory(productSku.getSeckillSku().getSeckillInventory().getValue());
            po.setSeckillPrice(productSku.getSeckillSku().getSeckillPrice().getValue());
            po.setSeckillLimitBuy(productSku.getSeckillSku().getSeckillLimitBuy().getValue());
            po.setSeckillTotalInventory(productSku.getSeckillSku().getSeckillTotalInventory().getValue());
        }
        if(productSku.getPackingNumber()!=null){
            po.setPackingNumber(productSku.getPackingNumber().getValue());
        }
        if(productSku.getCartonSize()!=null){
            po.setCartonSizeHeight(productSku.getCartonSize().getHeight());
            po.setCartonSizeLength(productSku.getCartonSize().getLength());
            po.setCartonSizeWidth(productSku.getCartonSize().getWidth());
        }
        return po;
    }

    public static List<ProductSku> convertList(List<ProductSkuPO> list){
        List<ProductSku> resList =new ArrayList<>();
        if(list!=null){
            list.forEach(x-> resList.add(convert(x)));
        }
        return  resList;
    }


//
//    public static ProductSkuPO convert2ProductSkuDO(
//            String productSkuId, String tenantId, ProductSku productSku){

//        return po;
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
