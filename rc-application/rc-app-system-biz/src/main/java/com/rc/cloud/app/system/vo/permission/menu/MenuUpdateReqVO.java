package com.rc.cloud.app.system.vo.permission.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 菜单更新 Request VO
 */
@Schema(description = "管理后台 - 菜单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuUpdateReqVO extends MenuBaseVO {

    @Schema(description = "菜单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "菜单编号不能为空")
    private String id;

}
