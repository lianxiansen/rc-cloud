package com.rc.cloud.app.operate.appearance.admin.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品SKU详情")
public class ProductSkuDetailResponse {


    @Schema(description = "属性1")
    private String attribute1;

    @Schema(description = "属性值1")
    private String attributeValue1;

    @Schema(description = "属性2")
    private String attribute2;

    @Schema(description = "属性值2")
    private String attributeValue2;

    @Schema(description = "图片")
    private String skuImage;

    @Schema(description = "价格")
    private String price;

    @Schema(description = "重量")
    private String weight;

    @Schema(description = "装箱数")
    private int packingNumber;

    @Schema(description = "箱规")
    private String cartonSize;

}
