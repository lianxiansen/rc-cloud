package com.rc.cloud.app.operate.appearance.admin.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * "attributes":[
 *     {"name":"颜色","value":"红","sort":9},
 *     {"name":"颜色","value":"黄","sort":9},
 *     {"name":"颜色","value":"蓝","sort":9},
 *     {"name":"尺寸","value":"X","sort":9},
 *     {"name":"尺寸","value":"XL","sort":9}
 * ]
 */
@Data
public class ProductAttributeSaveRequest {


    @Schema(description = "商品规格名比如：颜色")
    @NotBlank(message = "商品规格名不能为空")
    private String name;

    @Schema(description = "商品规格值名比如：红")
    @NotBlank(message = "商品规格值不能为空")
    private String value;

    @Schema(description = "排序")
    @Min(value = 0, message = "排序值最小为0")
    @Max(value = 99, message = "排序值最大为99")
    private Integer sort;

}
