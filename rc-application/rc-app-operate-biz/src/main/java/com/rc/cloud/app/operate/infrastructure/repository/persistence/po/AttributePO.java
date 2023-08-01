package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("attribute")
@Data
@EqualsAndHashCode(callSuper = true)
public class AttributePO extends TenantBaseDO {

    private static final long serialVersionUID = 2234345L;

    private Long id;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Integer sort;
}
