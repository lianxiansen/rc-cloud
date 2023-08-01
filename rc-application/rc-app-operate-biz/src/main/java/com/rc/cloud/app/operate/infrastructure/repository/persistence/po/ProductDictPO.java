package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_dict")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDictPO extends TenantBaseDO {

    private static final long serialVersionUID = 55322L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("product_id")
    private String productId;

    @TableField("dict_key")
    private String dictKey;

    @TableField("dict_value")
    private String dictValue;

    @TableField("sort")
    private Integer sort;
}
