package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


@Data
@Accessors(chain = true)
@Schema(description = "产品自定义类别获取request")
public class CustomClassificationGetDTO {

    @Schema(description = "产品自定义分类唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "72f7ae9e-2ff8-45aa-b61b-59ee900")
    @NotBlank(message = "产品自定义分类唯一标识不为空")
    private String id;

}
