package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: BrandQueryDTO
 * @Author: liandy
 * @Date: 2023/7/7 11:09
 * @Description: 品牌查询
 */
@Data
@Accessors(chain = true)
public class BrandQueryDTO{
    @Schema(description = "品牌名称", example = "振信")
    private String name;
}
