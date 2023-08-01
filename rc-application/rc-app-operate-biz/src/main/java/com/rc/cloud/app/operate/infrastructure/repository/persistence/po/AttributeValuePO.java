package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("attribute_value")
@Data
@EqualsAndHashCode(callSuper = true)
public class AttributeValuePO extends TenantBaseDO {

    private static final long serialVersionUID = 2453461L;

    private String id;

    @TableField("attribute_id")
    private String attributeId;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Integer sort;
}
