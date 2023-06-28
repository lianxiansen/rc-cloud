package com.rc.cloud.app.operate.domain.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.product.valobj.*;

import java.math.BigDecimal;

public class ProductSkuEntity extends Entity {


    private ProductSkuId productSkuId;

    private TenantId tenantId;

    private ProductId productId;

    private String skuCode;


    private SupplyPrice supplyPrice;

    private Weight weight;

    private OutId outId;

    private boolean hasImageFlag;

    private LimitBuy limitBuy;

    private Price price;

    private Inventory inventory;

    private SeckillSku seckillSku;

    /**
     * 排序
     */
    private Sort sort;
}
