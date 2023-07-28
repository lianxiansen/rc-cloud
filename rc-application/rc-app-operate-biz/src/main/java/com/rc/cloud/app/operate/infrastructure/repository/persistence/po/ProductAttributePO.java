package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
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
public class ProductAttributePO extends BaseDO {

    private static final long serialVersionUID = 245345L;

    private String id;

    @TableField("tenant_id")
    private String tenantId;

    @TableField("product_id")
    private String productId;

    @TableField("content")
    private String content;

}
