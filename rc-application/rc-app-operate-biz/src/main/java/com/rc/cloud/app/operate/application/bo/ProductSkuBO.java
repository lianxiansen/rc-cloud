package com.rc.cloud.app.operate.application.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;

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
