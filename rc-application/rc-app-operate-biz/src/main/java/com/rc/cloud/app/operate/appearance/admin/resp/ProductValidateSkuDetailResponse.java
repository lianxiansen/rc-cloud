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

    @Schema(description = "属性1")
    private String attribute1;

    @Schema(description = "属性值1")
    private String attributeValue1;

    @Schema(description = "属性2")
    private String attribute2;

    @Schema(description = "属性值2")
    private String attributeValue2;


}
