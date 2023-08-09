package com.rc.cloud.app.operate.appearance.admin.v1.req;

import com.rc.cloud.common.core.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: BrandQueryPageRequest
 * @Author: liandy
 * @Date: 2023/7/7 11:09
 * @Description: 品牌分页查询
 */
@Data
@Accessors(chain = true)
public class BrandQueryPageRequest extends PageParam {
    @Schema(description = "品牌名称", example = "振信")
    private String name;
}
