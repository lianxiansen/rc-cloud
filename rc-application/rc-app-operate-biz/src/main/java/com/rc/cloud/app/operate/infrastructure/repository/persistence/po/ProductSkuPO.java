package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@TableName("product_sku")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSkuPO extends TenantBaseDO {


    private static final long serialVersionUID = 23523L;

    private String id;

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

    @TableField("sort")
    private Integer sort;

    @TableField("carton_size_height")
    private int cartonSizeHeight;


    @TableField("carton_size_width")
    private int cartonSizeWidth;

    @TableField("carton_size_length")
    private int cartonSizeLength;

    @TableField("packing_number")
    private int packingNumber;


}
