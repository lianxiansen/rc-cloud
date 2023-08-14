package com.rc.cloud.api.product.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品图片response")
@NoArgsConstructor
public class ProductImageResponse {


    @Schema(description = "图片id")
    private String id;

    @Schema(description = "图片url")
    private String url;

    @Schema(description = "图片sort")
    private int sort;

    public ProductImageResponse(String id,String url, int sort) {
        this.id =id;
        this.url = url;
        this.sort = sort;
    }
}
