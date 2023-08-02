package com.rc.cloud.app.marketing.domain.entity.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @ClassName ProductItem
 * @Author liandy
 * @Date 2023/7/29 10:59
 * @Description TODO
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class ProductItem {
    private String spuId;
    private String skuId;
    private String productId;
    /**
     * 商品属性名称
     */
    private String attribute;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 商品库存数量
     */
    private int num;

    /**
     * 商品图片地址
     */
    private String image;

    public BigDecimal getAmount(){
        return new BigDecimal(num).multiply(price);
    }
}
