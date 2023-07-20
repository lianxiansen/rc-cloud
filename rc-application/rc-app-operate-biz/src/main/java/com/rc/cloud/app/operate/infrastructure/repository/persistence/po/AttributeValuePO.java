package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("attribute_value")
@Data
@EqualsAndHashCode(callSuper = true)
public class AttributeValuePO extends BaseDO {

    private static final long serialVersionUID = 2453461L;

    @TableField("id")
    private Long id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("attribute_id")
    private Long attributeId;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Integer sort;
}
