package com.rc.cloud.app.operate.application.bo;

import com.baomidou.mybatisplus.annotation.TableName;
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
     * 产品属性唯一Id
     */
    private String productuniqueid;

    /**
     * 产品信息视图
     */
    private ProductSkuBO productSkuBO;

    /**
     * 产品视图
     */
    private ProductBO productBO;
    /**
     * 店铺视图
     */
    private ShopBO shopBO;

    /**
     * 数量
     */
    private Integer num;

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
