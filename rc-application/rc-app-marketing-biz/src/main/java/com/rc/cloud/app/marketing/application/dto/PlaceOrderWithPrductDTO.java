package com.rc.cloud.app.marketing.application.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName PlaceOrderReq
 * @Author liandy
 * @Date 2023/7/29 15:45
 * @Description TODO
 * @Version 1.0
 */
@Data
public class PlaceOrderWithPrductDTO {
    /**
     * 商品唯一标识
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImage;
    /**
     * 商品货号
     */
    private String productArticleNo;



    /**
     * 商品项属性名称
     */
    private String productAttribute;
    /**
     * 商品项单价
     */
    private BigDecimal productPrice;
    /**
     * 商品项数量
     */
    private int quantity;


}
