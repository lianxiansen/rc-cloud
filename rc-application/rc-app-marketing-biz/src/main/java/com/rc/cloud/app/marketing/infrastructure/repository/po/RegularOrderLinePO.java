package com.rc.cloud.app.marketing.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @ClassName OrderItemPO
 * @Author liandy
 * @Date 2023/8/3 10:49
 * @Description 订单项持久化对象
 * @Version 1.0
 */
@TableName("regular_order_line")
@Data
@EqualsAndHashCode(callSuper = true)
public class RegularOrderLinePO extends BaseDO {
    /**
     * 订单项标识
     */
    private String id;
    /**
     * 订单标识
     */
    @TableField("regular_order_id")
    private String regularOrderId;

    /**
     * 商品唯一标识
     */
    @TableField("product_id")
    private String productId;
    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;
    /**
     * 商品图片
     */
    @TableField("product_image")
    private String productImage;
    /**
     * 商品货号
     */
    @TableField("product_article_no")
    private String productArticleNo;

    /**
     * 商品属性名称
     */
    @TableField("product_attribute")
    private String productAttribute;
    /**
     * 商品单价
     */
    @TableField("product_price")
    private BigDecimal productPrice;
    /**
     * 商品数量
     */
    @TableField("product_quality")
    private int productQuality;

    /**
     * 商品金额
     */
    @TableField("product_amount")
    private BigDecimal productAmount;
}
