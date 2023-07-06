package com.rc.cloud.app.operate.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_image")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductImageDO {


    private static final long serialVersionUID = 23434L;

    @TableField("id")
    private String Id;
    /**
     * 租户id
     */
    @TableField("tenant_id")
    private String tenantId;

    @TableField("product_id")
    private String productId;


    @TableField("url")
    private String url;

    /**
     * 是否是默认
     */
    //@TableField("default_flag")
    //private Boolean defaultFlag;


    @TableField("sort_id")
    private Integer sortId;

}
