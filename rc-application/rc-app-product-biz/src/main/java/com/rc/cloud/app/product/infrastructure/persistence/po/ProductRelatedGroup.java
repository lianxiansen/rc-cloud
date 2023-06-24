package com.rc.cloud.app.product.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_related_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductRelatedGroup extends BaseDO {


    private static final long serialVersionUID = 265345L;

    @TableField("id")
    private Long Id;

    @TableField("name")
    private Long name;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_id")
    private Long productId;

    /**
     * 相关商品id
     */
    @TableField("related_product_id")
    private Long relatedProductId;

    @TableField("sort_id")
    private Integer sortId;

}
