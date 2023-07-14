package com.rc.cloud.app.system.vo.dict.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 数据字典精简 Response VO
 */
@Schema(description = "管理后台 - 数据字典精简 Response VO")
@Data
public class DictDataSimpleRespVO {

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "gender")
    private String dictType;

    @Schema(description = "字典键值", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String value;

    @Schema(description = "字典标签", requiredMode = Schema.RequiredMode.REQUIRED, example = "男")
    private String label;

    @Schema(description = "颜色类型,default、primary、success、info、warning、danger", example = "default")
    private String colorType;
    @Schema(description = "css 样式", example = "btn-visible")
    private String cssClass;

}
