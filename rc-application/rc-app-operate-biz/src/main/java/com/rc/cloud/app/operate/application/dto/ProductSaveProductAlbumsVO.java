package com.rc.cloud.app.operate.application.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ProductSaveProductAlbumsVO {

    @NotBlank(message = "请设置相册图片路径")
    @Pattern(regexp = "^((https://jintian-file.oss-cn-hangzhou.aliyuncs.com/)|(https://cbu01.alicdn.com/img/ibank/))[0-9a-zA-Z/!?_-]+((.jpg)|(.jpeg)|(.png)|(.JPG)|(.JPEG)|(.PNG)|(.Jpg)|(.Jpeg)|(.Png)|(.gif)|(.GIF)|(.Gif))$", message = "相册图片错误：不支持的url")
    private String image;

    private int sort_id;
}