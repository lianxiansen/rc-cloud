package com.rc.cloud.app.operate.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_attribute")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductSkuImageDO {


    private static final long serialVersionUID = 2213123L;

    @TableField("id")
    private Long Id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_sku_id")
    private Long productSkuId;

    @TableField("url")
    private String url;

    /**
     * 是否是默认
     */
    @TableField("default_flag")
    private Boolean defaultFlag;


    @TableField("sort_id")
    private Integer sortId;

}
