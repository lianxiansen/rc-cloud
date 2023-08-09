package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Schema(description = "修改商品explosivesFlag请求数据")
public class ProductChangeExplosivesDTO {

    public String productId;

    @Schema(description = "是否是爆品")
    public boolean explosivesFlag;

    @Schema(description = "是否是爆品图片")
    @Pattern(regexp = "^((https://)|(http://))[0-9a-zA-Z/!?.:_-]+((.jpg)|(.jpeg)|(.png)|(.JPG)|(.JPEG)|(.PNG)|(.Jpg)|(.Jpeg)|(.Png)|(.gif)|(.GIF)|(.Gif))$", message = "爆品图片Url格式不正确")
    private String explosivesImage;


}
