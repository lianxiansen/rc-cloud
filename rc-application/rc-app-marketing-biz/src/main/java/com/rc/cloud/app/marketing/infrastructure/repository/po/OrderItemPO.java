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
@TableName("regular_order_item")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemPO extends BaseDO {
    /**
     * 订单项标识
     */
    private String id;
    /**
     * 订单标识
     */
    @TableField("order_id")
    private String orderId;

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
     * 商品项id
     */
    @TableField("product_item_id")
    private String productItemId;

    /**
     * 商品项名称
     */
    @TableField("product_item_name")
    private String productItemName;
    /**
     * 商品项图片地址
     */
    @TableField("product_item_image")
    private String productItemImage;
    /**
     * 商品项属性名称
     */
    @TableField("product_item_attribute")
    private String productItemAttribute;
    /**
     * 商品项单价
     */
    @TableField("product_item_price")
    private BigDecimal productItemPrice;
    /**
     * 商品项数量
     */
    @TableField("product_item_quantity")
    private int productItemQuantity;

    /**
     * 商品项金额
     */
    @TableField("product_item_amount")
    private BigDecimal productItemAmount;
}
