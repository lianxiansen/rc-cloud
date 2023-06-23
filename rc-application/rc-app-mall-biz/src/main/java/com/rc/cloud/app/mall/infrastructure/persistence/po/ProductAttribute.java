package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_attribute")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductAttribute {

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
