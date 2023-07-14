package com.rc.cloud.app.system.vo.dept.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:49
 * @description 部门列表 Request VO
 */
@Schema(description = "管理后台 - 部门列表 Request VO")
@Data
public class DeptListReqVO {

    @Schema(description = "部门名称,模糊匹配", example = "RC")
    private String name;

    @Schema(description = "展示状态,参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

}
