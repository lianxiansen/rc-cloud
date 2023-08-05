package com.rc.cloud.api.product.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAndSkuBO {

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
