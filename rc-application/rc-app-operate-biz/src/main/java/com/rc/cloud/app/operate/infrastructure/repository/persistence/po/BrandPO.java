package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("brand")
@Data
@EqualsAndHashCode(callSuper = true)
public class BrandPO extends BaseDO {


    private static final long serialVersionUID = 2343242L;
    private String id;

    @TableField("name")
    private String name;
    @TableField("logo")
    private String logo;
    @TableField("type")
    private String type;

    @TableField("enabled_flag")
    private boolean enabledFlag;

    @TableField("sort")
    private Integer sort;

}
