package com.rc.cloud.app.operate.appearance.admin.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class ProductSkuImageSaveRequest {

    @Schema(description = "地址")
    @Pattern(regexp = "^((https://)|(http://))[0-9a-zA-Z/!?.:_-]+((.jpg)|(.jpeg)|(.png)|(.JPG)|(.JPEG)|(.PNG)|(.Jpg)|(.Jpeg)|(.Png)|(.gif)|(.GIF)|(.Gif))$", message = "商品图片Url格式不正确")
    private String url;

    @Schema(description = "排序")
    @Min(value = 0, message = "排序值最小为0")
    @Max(value = 99, message = "排序值最大为99")
    private Integer sort;


}
