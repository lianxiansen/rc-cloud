package com.rc.cloud.app.operate.appearance.admin.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品SKU图片")
public class ProductSkuImageResponse {


    @Schema(description = "图片url")
    private String url;

    @Schema(description = "图片sort")
    private int sort;

    public ProductSkuImageResponse(String url, int sort) {
        this.url = url;
        this.sort = sort;
    }

}
