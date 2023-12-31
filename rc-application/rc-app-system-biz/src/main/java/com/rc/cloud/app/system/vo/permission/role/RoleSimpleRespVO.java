package com.rc.cloud.app.system.vo.permission.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 角色精简信息 Response VO
 */
@Schema(description = "管理后台 - 角色精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSimpleRespVO {

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String id;

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    private String name;

}
