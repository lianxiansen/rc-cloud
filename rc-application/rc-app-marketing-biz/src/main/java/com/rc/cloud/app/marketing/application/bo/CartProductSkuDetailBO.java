package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-28 9:17
 * @description TODO
 */
@Data
public class CartProductSkuDetailBO {
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

    private List<String> skuAttributes;

    private String productImage;

    private String skuImage;

    private int packingNumber;

    private int cartonSizeLength;

    private int cartonSizeWidth;

    private int cartonSizeHeight;
}
