package com.rc.cloud.app.system.vo.permission.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Schema(description = "管理后台 - 角色创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleCreateReqVO extends RoleBaseVO {

    @Schema(description = "菜单编号列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1024, 2048]")
    private Set<Long> menuIds;

}
