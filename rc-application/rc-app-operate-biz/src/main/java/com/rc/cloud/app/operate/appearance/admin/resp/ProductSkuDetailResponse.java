package com.rc.cloud.app.operate.appearance.admin.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品SKU详情")
public class ProductSkuDetailResponse {


    @Schema(description = "skuid")
    private String id;

    @Schema(description = "属性")
    private List<ProductSkuAttributeResponse> attributes;

    @Schema(description = "图片")
    private List<ProductSkuImageResponse> albums;

    @Schema(description = "价格")
    private String price;

    @Schema(description = "重量")
    private String weight;

    @Schema(description = "装箱数")
    private int packingNumber;

    @Schema(description = "箱规-长")
    private int cartonSizeLength;

    @Schema(description = "箱规-宽")
    private int cartonSizeWidth;

    @Schema(description = "箱规-高")
    private int cartonSizeHeight;

    @Schema(description = "库存")
    private long inventory;

}
