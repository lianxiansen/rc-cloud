package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


@Data
@Accessors(chain = true)
@Schema(description = "产品自定义分类创建request")
public class CustomClassificationCreateDTO {

    @Schema(description = "产品自定义分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "极简风")
    @NotBlank(message = "产品自定义分类名称不能为空")
    private String name;

    @Schema(description = "自定义分类图片")
    private String customClassificationImage;

    @Schema(description = "自定义分类商品海报图片")
    private String productPoster;

    @Schema(description = "自定义分类商品海报")
    private String customClassificationPoster;

    @Schema(description = "父级产品分类唯一标识")
    private String parentId;
    @Schema(description = "状态，是否启用")
    private Boolean enabled;
    @Schema(description = "排序")
    private Integer sort;

}
