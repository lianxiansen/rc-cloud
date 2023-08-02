package com.rc.cloud.app.operate.appearance.admin.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductSkuAttributeSaveRequest {

    @Schema(description = "属性名")
    private String name;

    @Schema(description = "属性值")
    private String value;

    @Schema(description = "排序")
    private Integer sort;

}
