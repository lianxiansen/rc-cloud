package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.common.core.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: CreateBrandDTO
 * @Author: liandy
 * @Date: 2023/7/7 11:09
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class BrandQueryPageDTO extends PageParam {
    @Schema(description = "品牌名称", example = "振信")
    private String name;
}
