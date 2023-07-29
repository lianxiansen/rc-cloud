package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.cart.identifier.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String userid;

    /**
     * 类型 1.产品 2.拼单 3.秒杀 4.砍价
     */
    private Integer type;

    /**
     * 产品Id
     */
    private String productid;

    /**
     * 产品属性唯一Id
     */
    private String productuniqueid;

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
    @TableField("cartonsizelength")
    private int cartonSizeLength;
    /**
     * 产品高
     */
    @TableField("cartonsizeheight")
    private int cartonSizeHeight;
    /**
     * 产品宽
     */
    @TableField("cartonsizewidth")
    private int cartonSizeWidth;
    /**
     * 产品规格属性
     */
    @TableField("skuattributes")
    private String skuAttributes;
    /**
     * 产品价格
     */
    private BigDecimal price;

    /**
     * 货号
     */
    private String outid;

    /**
     * 产品重量
     */
    private BigDecimal weight;

    /**
     * 产品装箱数
     */
    @TableField("packingnumber")
    private int packingNumber;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 店铺id
     */
    private String shopid;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 是否购买 0：未购买，1：已购买
     */
    private Integer payed;

    /**
     * 是否为立即购买
     */
    private Integer newstate;

    /**
     * 拼团id
     */
    private String combinationid;

    /**
     * 秒杀产品id
     */
    private String seckillid;

    /**
     * 砍价id
     */
    private String bargainid;
}
