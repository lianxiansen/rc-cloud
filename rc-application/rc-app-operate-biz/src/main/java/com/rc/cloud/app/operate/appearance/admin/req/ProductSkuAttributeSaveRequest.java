package com.rc.cloud.app.operate.appearance.admin.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ProductSkuAttributeSaveRequest {

    @Schema(description = "属性名")
    @NotBlank(message = "商品规格名不能为空")
    private String name;

    @Schema(description = "属性值")
    @NotBlank(message = "商品规格值不能为空")
    private String value;

    @Schema(description = "排序")
    @Min(value = 0, message = "排序值最小为0")
    @Max(value = 99, message = "排序值最大为99")
    private Integer sort;


}
