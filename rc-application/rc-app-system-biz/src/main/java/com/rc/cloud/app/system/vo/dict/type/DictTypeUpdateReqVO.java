package com.rc.cloud.app.system.vo.dict.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 字典类型更新 Request VO
 */
@Schema(description = "管理后台 - 字典类型更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeUpdateReqVO extends DictTypeBaseVO {

    @Schema(description = "字典类型编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "字典类型编号不能为空")
    private String id;

}
