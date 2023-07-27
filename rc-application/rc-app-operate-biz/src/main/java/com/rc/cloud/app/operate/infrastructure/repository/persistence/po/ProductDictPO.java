package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_dict")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDictPO {

    private static final long serialVersionUID = 55322L;

    private String id;

    @TableField("tenant_id")
    private String tenantId;

    @TableField("product_id")
    private String productId;

    @TableField("dict_key")
    private String key;

    @TableField("dict_value")
    private String value;

    @TableField("sort")
    private Integer sort;
}
