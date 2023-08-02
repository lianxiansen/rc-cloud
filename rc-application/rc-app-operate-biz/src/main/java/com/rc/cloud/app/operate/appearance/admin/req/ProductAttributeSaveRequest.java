package com.rc.cloud.app.operate.appearance.admin.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
    private String name;

    @Schema(description = "商品规格值名比如：红")
    private String value;

    @Schema(description = "排序")
    private Integer sort;

}
