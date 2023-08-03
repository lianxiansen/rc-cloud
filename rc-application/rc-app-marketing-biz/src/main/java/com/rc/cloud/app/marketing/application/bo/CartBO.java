package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author WJF
 * @create 2023-07-25 8:39
 * @description TODO
 */
@Data
public class CartBO {

    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 类型 1.产品 2.拼单 3.秒杀 4.砍价
     */
    private Integer type;

    /**
     * 产品信息
     */
    private CartProductDetailBO cartProductDetailBO;

    /**
     * 产品sku信息
     */
    private CartProductSkuDetailBO cartProductSkuDetailBO;

    /**
     * 店铺视图
     */
    private ShopBO shopBO;

    /**
     * 店铺视图
     */
    private String shopId;

    /**
     * 数量
     */
    private Integer num;

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

    /**
     * 购物车是否失效
     */
    private Integer state;
}
