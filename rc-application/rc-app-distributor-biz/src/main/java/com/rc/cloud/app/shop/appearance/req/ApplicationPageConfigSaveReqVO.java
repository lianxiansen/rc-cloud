package com.rc.cloud.app.shop.appearance.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-21 14:22
 * @description TODO
 */
@Data
public class ApplicationPageConfigSaveReqVO {
    @Schema(description = "id")
    private String id;

    @Schema(description = "页面配置数据")
    private String template;
}
