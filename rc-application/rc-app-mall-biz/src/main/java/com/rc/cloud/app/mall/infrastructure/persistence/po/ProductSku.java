package com.rc.cloud.app.mall.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@TableName("product_sku")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSku extends BaseDO {


    private static final long serialVersionUID = 23523L;

    @TableField("id")
    private Long Id;
    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_id")
    private Long productId;


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
    private BigDecimal outId;

    @TableField("has_image_flag")
    private Boolean hasImageFlag;

    @TableField("limit_buy")
    private Integer limitBuy;

    @TableField("price")
    private BigDecimal price;

    /**
     * 库存
     */
    @TableField("inventory")
    private Integer inventory;

    @TableField("seckill_limit_buy")
    private Integer seckillLimitBuy;

    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    @TableField("seckill_inventory")
    private Integer seckillInventory;

    @TableField("seckill_total_inventory")
    private Integer seckillTotalInventory;

    @TableField("sort_id")
    private Integer sortId;

}