package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品详情
 * @Author taotianhong
 * @Date 2021/3/25
 * @Description:
 */
@TableName("product_detail")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDetailPO {


    private static final long serialVersionUID = 34322L;

    @TableField("id")
    private String Id;
    /**
     * 租户id
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 品牌ID
     */
    @TableField("product_id")
    private String productId;


    @TableField("detail")
    private String detail;
}
