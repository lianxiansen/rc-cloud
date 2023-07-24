package com.rc.cloud.app.shop.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-21 14:58
 * @description TODO
 */
@Data
public class ApplicationPageConfigPublishDTO {
    @Schema(description = "id")
    private String id;

    @Schema(description = "页面配置数据")
    private String template;
}
