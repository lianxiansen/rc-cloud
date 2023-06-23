package com.rc.cloud.app.mall.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@TableName("product_image")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductImage  {


    private static final long serialVersionUID = 23434L;

    @TableField("id")
    private Long Id;
    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_id")
    private Long productId;


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
