package com.rc.cloud.app.mall.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("brand")
@Data
@EqualsAndHashCode(callSuper = true)
public class Brand extends BaseDO {


    private static final long serialVersionUID = 2343242L;
    @TableField("id")
    private Long Id;

    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("enabled_flag")
    private Boolean enabledFlag;

    @TableField("sort_id")
    private Integer sortId;

}
