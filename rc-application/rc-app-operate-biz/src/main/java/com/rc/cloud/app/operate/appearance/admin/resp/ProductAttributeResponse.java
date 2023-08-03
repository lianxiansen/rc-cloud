package com.rc.cloud.app.operate.appearance.admin.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品规格详情")
public class ProductAttributeResponse {

    @Schema(description = "商品属性名")
    private String name;

    @Schema(description = "商品属性值")
    private String value;

    @Schema(description = "排序")
    private int sort;
}
