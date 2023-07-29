package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_sku_image")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductSkuImagePO extends BaseDO {


    private static final long serialVersionUID = 2213123L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("tenant_id")
    private String tenantId;

    @TableField("product_sku_id")
    private String productSkuId;

    @TableField("url")
    private String url;


    @TableField("sort")
    private Integer sort;

}
