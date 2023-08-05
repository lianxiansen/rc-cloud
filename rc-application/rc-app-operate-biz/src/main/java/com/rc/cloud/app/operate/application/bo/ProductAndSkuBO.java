package com.rc.cloud.app.operate.application.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAndSkuBO {


    /**
     * 商品信息
     */

    private String productId;

    private String skuId;

    private String productName;

    private String productImage;


    private String attribute1;

    private String attributeValue1;

    private String attribute2;

    private String attributeValue2;

    private BigDecimal price;

}
