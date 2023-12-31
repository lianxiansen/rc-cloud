package com.rc.cloud.app.system.vo.dict.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 字典数据创建 Request VO
 */
@Schema(description = "管理后台 - 字典数据创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataCreateReqVO extends DictDataBaseVO {

}
