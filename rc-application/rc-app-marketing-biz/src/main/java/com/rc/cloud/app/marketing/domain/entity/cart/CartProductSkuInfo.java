package com.rc.cloud.app.marketing.domain.entity.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-07 13:09
 * @description TODO
 */
@Data
public class CartProductSkuInfo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "skuId")
    private String skuId;

    @Schema(description = "productId")
    private String productId;

    @Schema(description = "商品名")
    private String productName;

    @Schema(description = "价格")
    private String price;

    @Schema(description = "sku图片")
    private String productImage;

    //{颜色,尺寸}          {黄色，Y}
    @Schema(description = "属性")
    private List<String> attributes;

    //{颜色,尺寸}          {黄色，Y}
    @Schema(description = "属性值")
    private List<String> attributeValues;

}
