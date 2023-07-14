package com.rc.cloud.app.system.vo.permission.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 赋予角色数据权限 Request VO
 */
@Schema(description = "管理后台 - 赋予角色菜单 Request VO")
@Data
public class PermissionAssignRoleMenuReqVO {

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "角色编号不能为空")
    private String roleId;

    @Schema(description = "菜单编号列表", example = "1,3,5")
    private Set<String> menuIds = Collections.emptySet(); // 兜底

}
