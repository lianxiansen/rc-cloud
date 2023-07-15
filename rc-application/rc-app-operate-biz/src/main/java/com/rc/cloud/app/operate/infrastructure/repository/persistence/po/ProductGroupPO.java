package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductGroupPO extends BaseDO {

    private static final long serialVersionUID = 2343242L;
    @TableField("id")
    private String Id;

    @TableField("name")
    private String name;

    @TableField("tenant_id")
    private String tenantId;
    @TableField("product_id")
    private String productId;

}
