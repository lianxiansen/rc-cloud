package com.rc.cloud.app.operate.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@TableName("product_sku")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSkuDO extends BaseDO {


    private static final long serialVersionUID = 23523L;

    @TableField("id")
    private String Id;
    /**
     * 租户id
     */
    @TableField("tenant_id")
    private String tenantId;

    @TableField("product_id")
    private String productId;


    @TableField("sku_code")
    private String skuCode;

    /**
     * 供货价
     */
    @TableField("supply_price")
    private BigDecimal supplyPrice;

    @TableField("weight")
    private BigDecimal weight;

    @TableField("out_id")
    private String outId;

    @TableField("has_image_flag")
    private Boolean hasImageFlag;

    @TableField("limit_buy")
    private int limitBuy;

    @TableField("price")
    private BigDecimal price;

    /**
     * 库存
     */
    @TableField("inventory")
    private int inventory;

    @TableField("seckill_limit_buy")
    private int seckillLimitBuy;

    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    @TableField("seckill_inventory")
    private int seckillInventory;

    @TableField("seckill_total_inventory")
    private int seckillTotalInventory;

    @TableField("sort_id")
    private Integer sortId;

}
