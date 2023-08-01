package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
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
public class ProductAttributePO extends TenantBaseDO {

    private static final long serialVersionUID = 245345L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("product_id")
    private String productId;

    @TableField("content")
    private String content;

}
