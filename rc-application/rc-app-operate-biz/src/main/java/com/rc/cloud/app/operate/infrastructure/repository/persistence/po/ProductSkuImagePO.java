package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_attribute")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductSkuImagePO {


    private static final long serialVersionUID = 2213123L;

    @TableField("id")
    private String id;

    @TableField("tenant_id")
    private String tenantId;

    @TableField("product_sku_id")
    private String productSkuId;

    @TableField("url")
    private String url;


    @TableField("sort")
    private Integer sort;

}
