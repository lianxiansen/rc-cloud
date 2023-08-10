package com.rc.cloud.app.operate.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductSkuBO {


    private String id;

    private String skuCode;

    private BigDecimal supplyPrice;

    private BigDecimal weight;

    private String outId;

    private int limitBuy;

    private BigDecimal price;

    private long inventory;

    private int sort;

    private int seckillLimitBuy;

    private BigDecimal seckillPrice;

    private long seckillInventory;

    private int seckillTotalInventory;

    private String seckillBuyRate;

    private List<AttributeValueCombinationBO> skuAttributes;

    private List<ProductSkuImageBO> skuImages;

    private int packingNumber;

    private int cartonSizeLength;

    private int cartonSizeWidth;

    private int cartonSizeHeight;





}
