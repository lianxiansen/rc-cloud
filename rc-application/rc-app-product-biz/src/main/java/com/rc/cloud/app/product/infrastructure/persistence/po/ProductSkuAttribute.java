package com.rc.cloud.app.product.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_attribute")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductSkuAttribute {

    private static final long serialVersionUID = 21231L;

    @TableField("id")
    private Long Id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_sku_id")
    private Long productSkuId;

    @TableField("content")
    private String content;

}
