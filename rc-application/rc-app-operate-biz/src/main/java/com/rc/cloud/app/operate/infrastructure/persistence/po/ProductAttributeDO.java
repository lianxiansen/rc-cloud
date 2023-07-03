package com.rc.cloud.app.operate.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


//-- 存储[
//        -- {
//        --             "attribute_name" : "尺码",
//        --             "attribute_value" : "42",
//        --             "sort_id" : 42
//        --         },
//        --         {
//        --             "attribute_name" : "尺码",
//        --             "attribute_value" : "44",
//        --             "sort_id" : 42
//        --         },
//        --         {
//        --             "attribute_name" : "颜色",
//        --             "attribute_value" : "卡其色",
//        --             "sort_id" : 42
//        --         },
//        --         {
//        --             "attribute_name" : "尺码",
//        --             "attribute_value" : "39",
//        --             "sort_id" : 42
//        --         }
//        --     ]
//        -- ----------------------------
@TableName("product_attribute")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductAttributeDO {

    private static final long serialVersionUID = 245345L;

    @TableField("id")
    private Long Id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_id")
    private Long productId;

    @TableField("content")
    private String content;

}
