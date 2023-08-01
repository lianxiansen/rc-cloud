package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("platform_product_category")
@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformProductCategoryPO extends BaseDO {


    private static final long serialVersionUID = 3452342L;

    private String id;

    @TableField("name")
    private String name;

    @TableField("icon")
    private String icon;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("layer")
    private Integer layer;

    @TableField("enabled_flag")
    private Boolean enabledFlag;

    @TableField("sort")
    private Integer sort;


}
