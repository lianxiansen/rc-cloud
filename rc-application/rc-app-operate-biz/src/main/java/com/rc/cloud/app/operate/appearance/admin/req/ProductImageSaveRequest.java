package com.rc.cloud.app.operate.appearance.admin.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductImageSaveRequest {

    @Schema(description = "图片id")
    private String id;

    @Schema(description = "图片地址")
    private String url;

    @Schema(description = "排序")
    private Integer sort;

}
