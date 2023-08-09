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
public class BrandQueryRequest {
    @Schema(description = "品牌名称", example = "振信")
    private String name;
}
