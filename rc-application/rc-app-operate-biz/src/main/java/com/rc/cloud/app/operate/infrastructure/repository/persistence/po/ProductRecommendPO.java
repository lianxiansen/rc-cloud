package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_recommend")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductRecommendPO extends TenantBaseDO {

    private static final long serialVersionUID = 2343242L;
    private String id;
    @TableField("product_id")
    private String productId;
    @TableField("recommend_product_id")
    private String recommendProductId;
}
