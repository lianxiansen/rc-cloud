package com.rc.cloud.app.operate.appearance.admin.resp;

import com.rc.cloud.app.operate.application.bo.AttributeValueCombinationBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuImageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品SKU详情")
public class ProductValidateSkuDetailResponse {

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
