package com.rc.cloud.app.system.vo.dict.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 字典类型创建 Request VO
 */
@Schema(description = "管理后台 - 字典类型创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeCreateReqVO extends DictTypeBaseVO {

}
