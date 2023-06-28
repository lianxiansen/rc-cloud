package com.rc.cloud.app.operate.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("attribute_value")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDictDO {

    private static final long serialVersionUID = 55322L;

    @TableField("id")
    private Long Id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_id")
    private Long productId;

    @TableField("key")
    private String key;

    @TableField("value")
    private String value;

    @TableField("sort_id")
    private Integer sortId;
}
