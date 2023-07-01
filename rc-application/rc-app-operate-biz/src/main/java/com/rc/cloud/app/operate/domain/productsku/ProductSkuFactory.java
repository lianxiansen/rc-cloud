
package com.rc.cloud.app.operate.domain.productsku;

import com.rc.cloud.app.operate.domain.productsku.valobj.ProductId;
import com.rc.cloud.app.operate.domain.productsku.valobj.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ProductSkuFactory
 * @Author: liandy
 * @Date: 2023/6/30 13:41
 * @Description: 产品类目工厂
 */
@Component
public class ProductSkuFactory {
    @Autowired
    private ProductSkuRepository productCategoryRepository;


    public ProductSkuBuilder builder(ProductSkuId id, ProductId productId,
                                     com.rc.cloud.app.operate.domain.productsku.valobj.TenantId tenantId, Price price) {
        return new ProductSkuBuilder(id,productId, tenantId, price);
    }
    public ProductSkuReBuilder reBuilder(ProductSkuId id, ProductId productId,
                                         com.rc.cloud.app.operate.domain.productsku.valobj.TenantId tenantId, Price price) {
        return new ProductSkuReBuilder(id,productId, tenantId, price);
    }

    /**
     * 产品类目构造器
     */
    public class ProductSkuBuilder {

        private ProductSkuEntity productSkuEntity;
        private ProductSkuBuilder(ProductSkuId id, com.rc.cloud.app.operate.domain.productsku.valobj.ProductId productId,
                                  com.rc.cloud.app.operate.domain.productsku.valobj.TenantId tenantId, Price price) {
            productSkuEntity=new ProductSkuEntity(id,productId,tenantId,price);
        }

        public ProductSkuBuilder skuCode(String skuCode) {
            productSkuEntity.setSkuCode(skuCode);
            return this;
        }

        public ProductSkuBuilder supplyPrice(SupplyPrice supplyPrice) {
            productSkuEntity.setSupplyPrice(supplyPrice);
            return this;
        }

        public ProductSkuBuilder weight(Weight weight) {
            productSkuEntity.setWeight(weight);
            return this;
        }

        public ProductSkuBuilder outId(OutId outId) {
            productSkuEntity.setOutId(outId);
            return this;
        }

        public ProductSkuBuilder hasImageFlag(boolean hasImageFlag) {
            productSkuEntity.setHasImageFlag(hasImageFlag);
            return this;
        }

        public ProductSkuBuilder limitBuy(LimitBuy limitBuy) {
            productSkuEntity.setLimitBuy(limitBuy);
            return this;
        }

        public ProductSkuBuilder inventory(Inventory inventory) {
            productSkuEntity.setInventory(inventory);
            return this;
        }

        public ProductSkuBuilder seckillSku(SeckillSku seckillSku) {
            productSkuEntity.setSeckillSku(seckillSku);
            return this;
        }

        public ProductSkuEntity build() {
            return this.productSkuEntity;
        }
    }


    public class ProductSkuReBuilder {
        private ProductSkuEntity productSkuEntity;

        private ProductSkuReBuilder(ProductSkuId id, ProductId productId,
                                  com.rc.cloud.app.operate.domain.productsku.valobj.TenantId tenantId, Price price) {
            productSkuEntity=new ProductSkuEntity(id,productId,tenantId,price);
        }



        public ProductSkuReBuilder skuCode(String skuCode) {
            productSkuEntity.setSkuCode(skuCode);
            return this;
        }

        public ProductSkuReBuilder supplyPrice(SupplyPrice supplyPrice) {
            productSkuEntity.setSupplyPrice(supplyPrice);
            return this;
        }

        public ProductSkuReBuilder weight(Weight weight) {
            productSkuEntity.setWeight(weight);
            return this;
        }

        public ProductSkuReBuilder outId(OutId outId) {
            productSkuEntity.setOutId(outId);
            return this;
        }

        public ProductSkuReBuilder hasImageFlag(boolean hasImageFlag) {
            productSkuEntity.setHasImageFlag(hasImageFlag);
            return this;
        }

        public ProductSkuReBuilder limitBuy(LimitBuy limitBuy) {
            productSkuEntity.setLimitBuy(limitBuy);
            return this;
        }

        public ProductSkuReBuilder inventory(Inventory inventory) {
            productSkuEntity.setInventory(inventory);
            return this;
        }

        public ProductSkuReBuilder seckillSku(SeckillSku seckillSku) {
            productSkuEntity.setSeckillSku(seckillSku);
            return this;
        }
        public ProductSkuEntity rebuild() {
            return productSkuEntity;
        }
    }

}