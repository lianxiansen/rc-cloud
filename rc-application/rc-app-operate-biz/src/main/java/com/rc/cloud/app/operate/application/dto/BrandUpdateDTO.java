package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName: CreateBrandDTO
 * @Author: liandy
 * @Date: 2023/7/7 11:09
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class BrandUpdateDTO {
    @Schema(description = "品牌唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "f7570440-f052-462c-b6a8-984b701")
    @NotBlank(message = "品牌唯一标识不能为空")
    private String id;
    @Schema(description = "品牌名称", example = "振信")
    private String name;
    @Schema(description = "logo")
    private String logo;
    @Schema(description = "品牌类型", example = "自有")
    private String type;
    @Schema(description = "状态，是否启用", example = "true")
    private Boolean enabled;
    @Schema(description = "排序，最大值不能超过99", example = "99")
    @Max(value = 99,message = "最大值不能超过99")
    private Integer sort;
}
