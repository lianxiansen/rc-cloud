package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 自定义分类
 */
@TableName("custom_classification")
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomClassificationPO extends TenantBaseDO {


    private static final long serialVersionUID = 23434L;

    private String id;

    @TableField("name")
    private String name;

    @TableField("custom_classification_image")
    private String customClassificationImage;

    @TableField("product_poster")
    private String productPoster;

    @TableField("custom_classification_poster")
    private String customClassificationPoster;

    @TableField("enabled_flag")
    private Boolean enabledFlag;


    @TableField("sort")
    private Integer sort;

}
