package com.rc.cloud.app.marketing.application.dto;

import com.rc.cloud.common.core.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@Schema(description = "产品组合创建request")
public class ProductGroupCreateDTO {
    @Schema(description = "产品唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "72f7ae9e-2ff8-45aa-b61b-59ee900")
    @NotBlank(message = "产品唯一标识不能为空")
    private String productId;
    @Schema(description = "组合名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "厨房套装")
    @NotBlank(message = "组合名称不能为空")
    private String name;

}
