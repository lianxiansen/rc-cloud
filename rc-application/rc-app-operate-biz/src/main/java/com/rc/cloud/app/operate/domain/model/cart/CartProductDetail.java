package com.rc.cloud.app.operate.domain.model.cart;

import com.rc.cloud.app.operate.application.bo.AttributeValueCombinationBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuImageBO;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-28 9:17
 * @description TODO
 */
@Data
public class CartProductDetail {

    private ProductId productId;

    private String productName;

    private String productImage;

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
