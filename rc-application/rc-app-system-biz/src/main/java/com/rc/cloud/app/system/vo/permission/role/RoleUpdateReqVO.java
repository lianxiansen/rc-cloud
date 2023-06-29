package com.rc.cloud.app.system.vo.permission.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Schema(description = "管理后台 - 角色更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUpdateReqVO extends RoleBaseVO {

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "角色编号不能为空")
    private Long id;

    @Schema(description = "菜单编号列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1024, 2048]")
    private Set<Long> menuIds;

}
