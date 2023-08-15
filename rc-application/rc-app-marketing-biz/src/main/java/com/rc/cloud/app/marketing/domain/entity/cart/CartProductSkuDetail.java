package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WJF
 * @create 2023-08-02 16:20
 * @description TODO
 */
@Data
public class CartProductSkuDetail {
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

    private String skuImage;

    private int packingNumber;

    private int cartonSizeLength;

    private int cartonSizeWidth;

    private int cartonSizeHeight;
}
