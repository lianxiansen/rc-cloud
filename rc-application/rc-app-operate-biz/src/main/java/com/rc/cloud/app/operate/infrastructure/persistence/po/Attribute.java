package com.rc.cloud.app.operate.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("attribute")
@Data
@EqualsAndHashCode(callSuper = true)
public class Attribute extends BaseDO {

    private static final long serialVersionUID = 2234345L;

    @TableField("id")
    private Long Id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("name")
    private String name;

    @TableField("sort_id")
    private Integer sortId;
}
