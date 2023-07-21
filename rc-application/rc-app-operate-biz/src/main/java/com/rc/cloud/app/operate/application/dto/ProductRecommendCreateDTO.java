package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@Schema(description = "产品组合创建request")
public class ProductRecommendCreateDTO {
    @Schema(description = "产品唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "72f7ae9e-2ff8-45aa-b61b-59ee900")
    @NotBlank(message = "产品唯一标识不能为空")
    private String productId;
    @Schema(description = "推荐产品唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "72f7ae9e-2ff8-45aa-b61b-59ee900")
    @NotBlank(message = "推荐产品唯一标识不能为空")
    private String recommendProductId;


}
