package com.rc.cloud.app.system.vo.dept.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:49
 * @description 部门更新 Request VO
 */
@Schema(description = "管理后台 - 部门更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptUpdateReqVO extends DeptBaseVO {

    @Schema(description = "部门编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "部门编号不能为空")
    private String id;

}
