package com.rc.cloud.app.marketing.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WJF
 * @create 2023-07-25 8:39
 * @description TODO
 */
@TableName("order_cart")
@Data
public class CartPO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 货号
     */
    private String spuCode;

    /**
     * 类型 1.产品 2.拼单 3.秒杀 4.砍价
     */
    private Integer type;

    /**
     * 产品Id
     */
    private String productId;

    /**
     * 产品属性唯一Id
     */
    private String productUniqueid;

    /**
     * 产品skuCode
     */
    private String skuCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品图片
     */
    private String productImage;

    /**
     * 产品长
     */
    private int cartonSizeLength;
    /**
     * 产品高
     */
    private int cartonSizeHeight;
    /**
     * 产品宽
     */
    private int cartonSizeWidth;
    /**
     * 产品规格属性
     */
    private String skuAttributes;
    /**
     * 产品价格
     */
    private BigDecimal price;

    /**
     * 外部id
     */
    private String outId;

    /**
     * 产品重量
     */
    private BigDecimal weight;

    /**
     * 产品装箱数
     */
    private int packingNumber;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否购买 0：未购买，1：已购买
     */
    private Integer payed;

    /**
     * 是否为立即购买
     */
    private Integer newState;

    /**
     * 拼团id
     */
    private String combinationId;

    /**
     * 秒杀产品id
     */
    private String seckillId;

    /**
     * 砍价id
     */
    private String bargainId;
}
