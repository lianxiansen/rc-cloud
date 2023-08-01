package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_related_group")
public class ProductRelatedGroupPO extends TenantBaseDO {


    private static final long serialVersionUID = 265345L;

    private Long id;

    @TableField("name")
    private Long name;


    @TableField("product_id")
    private Long productId;

    /**
     * 相关商品id
     */
    @TableField("related_product_id")
    private Long relatedProductId;

    @TableField("sort")
    private Integer sort;


}
