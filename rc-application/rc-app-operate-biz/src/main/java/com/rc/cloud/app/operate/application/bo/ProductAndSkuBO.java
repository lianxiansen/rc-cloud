package com.rc.cloud.app.operate.application.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductAndSkuBO {


    /**
     * 商品信息
     */

    private String productId;

    private String skuId;

    private String productName;

    private String productImage;


    //{颜色,尺寸}          {黄色，Y}
    private List<String> attributes;

    //{颜色,尺寸}          {黄色，Y}
    private List<String> attributeValues;

    private BigDecimal price;

}
