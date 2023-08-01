package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductGroupPO extends TenantBaseDO {

    private static final long serialVersionUID = 2343242L;
    private String id;

    @TableField("name")
    private String name;

    @TableField("product_id")
    private String productId;

}
