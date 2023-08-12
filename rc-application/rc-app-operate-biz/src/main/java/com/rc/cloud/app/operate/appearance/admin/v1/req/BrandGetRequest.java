package com.rc.cloud.app.operate.appearance.admin.v1.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: BrandQueryRequest
 * @Author: liandy
 * @Date: 2023/7/7 11:09
 * @Description: 品牌查询
 */
@Data
@Accessors(chain = true)
public class BrandGetRequest {
    @Schema(description = "品牌id", example = "振信")
    private String id;
}
