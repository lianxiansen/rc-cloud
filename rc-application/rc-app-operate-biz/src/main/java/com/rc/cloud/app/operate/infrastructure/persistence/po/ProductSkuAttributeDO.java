package com.rc.cloud.app.operate.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


//-- ----------------------------
//        -- 商品SKU属性表
//        -- 存储[
//        -- {
//        --                     "attribute_name" : "颜色",
//        --                     "attribute_value" : "卡其色",
//        --                     "sort_id" : 42
//        --                 },
//        --                 {
//        --                     "attribute_name" : "尺码",
//        --                     "attribute_value" : "44",
//        --                     "sort_id" : 42
//        --                 }
//        --             ]
//        -- ----------------------------
@TableName("product_attribute")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductSkuAttributeDO {

    private static final long serialVersionUID = 21231L;

    @TableField("id")
    private String Id;

    @TableField("tenant_id")
    private String tenantId;

    @TableField("product_sku_id")
    private String productSkuId;

    @TableField("content")
    private String content;

}
