package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuAttributeSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuAttributeId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSkuConvert {

    public static ProductSku convert(ProductSkuId productSkuId,ProductId productId, TenantId tenantId,
                                  ProductSkuSaveDTO productSkuSaveDTO, boolean isCreate, ProductSku productSku){

        if(isCreate){
            productSku=new ProductSku(productSkuId, productId, tenantId);
        }
        //价格
        productSku =setPrice(productSkuSaveDTO.getPrice(),isCreate,productSku);
        productSku =setEnabledFlag(productSkuSaveDTO.getEnabledFlag(),isCreate,productSku);
        productSku =setInventory(productSkuSaveDTO.getInventory(),isCreate,productSku);
        productSku =setSort(productSkuSaveDTO.getSort(),isCreate,productSku);
        productSku =setWeight(productSkuSaveDTO.getWeight(),isCreate,productSku);
        productSku =setSupplyPrice(productSkuSaveDTO.getSupplyPrice(),isCreate,productSku);
        //sku图片
        productSku =setProductSkuImage(productSkuSaveDTO,isCreate,productSku);
        //sku属性
        productSku =setProductSkuAttibute(productSkuSaveDTO,isCreate,productSku);
        return productSku;
    }


    private static ProductSku setPrice(String price, boolean isCreate, ProductSku productSku){
        if(isCreate){
            productSku.setPrice(new Price(price));
        }else{
            if (price != null) {
                productSku.setPrice(new Price(price));
            }
        }
        return productSku;
    }

    private static ProductSku setEnabledFlag(Boolean enabledFlag, boolean isCreate, ProductSku productSku){
        if(isCreate){
            productSku.setEnabledFlag(enabledFlag==null?false:enabledFlag);
        }else{
            if (enabledFlag != null) {
                productSku.setEnabledFlag(enabledFlag);
            }
        }
        return productSku;
    }

    private static ProductSku setInventory(Integer inventory, boolean isCreate, ProductSku productSku){
        if(isCreate){
            productSku.setInventory(new Inventory(inventory));
        }else{
            if (inventory != null) {
                productSku.setInventory(new Inventory(inventory));
            }
        }
        return productSku;
    }

    private static ProductSku setSort(Integer sort, boolean isCreate, ProductSku productSku){
        if(isCreate){
            productSku.setSort(new Sort(sort));
        }else{
            if (sort != null) {
                productSku.setSort(new Sort(sort));
            }
        }
        return productSku;
    }

    private static ProductSku setWeight(String weight, boolean isCreate, ProductSku productSku){
        if(isCreate){
            productSku.setWeight(new Weight(weight));
        }else{
            if (weight != null) {
                productSku.setWeight(new Weight(weight));
            }
        }
        return productSku;
    }

    private static ProductSku setSupplyPrice(String supplyPrice, boolean isCreate, ProductSku productSku){
        if(isCreate){
            productSku.setSupplyPrice(new SupplyPrice(supplyPrice));
        }else{
            if (supplyPrice != null) {
                productSku.setSupplyPrice(new SupplyPrice(supplyPrice));
            }
        }
        return productSku;
    }

    private static ProductSku setProductSkuImage(ProductSkuSaveDTO dto,boolean isCreate, ProductSku productSku){
        List<ProductSkuImage> productImages = ProductSkuImageConvert.convertList(dto.getAlbums());
        if(isCreate){
            productSku.setSkuImageList(productImages);
        }
        if (dto.getAlbums() != null) {
            productSku.setSkuImageList(productImages);
        }
        return productSku;
    }

    private static ProductSku setProductSkuAttibute(ProductSkuSaveDTO dto, boolean isCreate,ProductSku productSku){
        List<ProductSkuAttributeSaveDTO> attributes = dto.getAttributes();
        ProductSkuAttribute productSkuAttribute = new ProductSkuAttribute(new ProductSkuAttributeId(dto.getAttributeId()));
        if(isCreate){
            if(attributes==null){
                throw  new IllegalArgumentException("attributes must be not null");
            }
            for (ProductSkuAttributeSaveDTO attribute : attributes) {
                productSkuAttribute.addSkuAttribute(attribute.getName(), attribute.getValue(), attribute.getSort());
            }
            productSku.setProductSkuAttribute(productSkuAttribute);
        }else{
            if(attributes!=null){
                for (ProductSkuAttributeSaveDTO attribute : attributes) {
                    productSkuAttribute.addSkuAttribute(attribute.getName(), attribute.getValue(), attribute.getSort());
                }
            }
        }
        return productSku;
    }

    public static ProductSkuBO convert(ProductSku productSku){
        ProductSkuBO bo=new ProductSkuBO();
        bo.setId(productSku.getId().id());
        if(productSku.getPrice()!=null){
            bo.setPrice(productSku.getPrice().getValue());
        }
        if(productSku.getLimitBuy()!=null){
            bo.setLimitBuy(productSku.getLimitBuy().getValue());
        }
        if(productSku.getWeight()!=null){
            bo.setWeight(productSku.getWeight().getValue());
        }
        if(productSku.getOutId()!=null){
            bo.setOutId(productSku.getOutId().getValue());
        }
        if(productSku.getSort()!=null){
            bo.setSort(productSku.getSort().getValue());
        }
        bo.setSkuCode(productSku.getSkuCode());
        if(productSku.getInventory()!=null){
            bo.setInventory(productSku.getInventory().getValue());
        }
        if(productSku.getSupplyPrice()!=null){
            bo.setSupplyPrice(productSku.getSupplyPrice().getValue());
        }
        if(productSku.getSeckillSku()!=null){
            SeckillSku seckillSku = productSku.getSeckillSku();
            if(seckillSku.getSeckillInventory()!=null){
                bo.setSeckillInventory(seckillSku.getSeckillInventory().getValue());
            }
            if(seckillSku.getSeckillPrice()!=null){
                bo.setSeckillPrice(seckillSku.getSeckillPrice().getValue());
            }
            if(seckillSku.getSeckillLimitBuy()!=null){
                bo.setSeckillLimitBuy(seckillSku.getSeckillLimitBuy().getValue());
            }
            if(seckillSku.getSeckillTotalInventory()!=null){
                bo.setSeckillTotalInventory(seckillSku.getSeckillTotalInventory().getValue());
            }
            bo.setSeckillBuyRate(seckillSku.getSeckillBuyRate());
        }

        return bo;
    }

    public static List<ProductSkuBO> convertList(List<ProductSku> productSkuList) {
        List<ProductSkuBO> resList =new ArrayList<>();
        productSkuList.forEach(x->resList.add(convert(x)));
        return resList;
    }
}
