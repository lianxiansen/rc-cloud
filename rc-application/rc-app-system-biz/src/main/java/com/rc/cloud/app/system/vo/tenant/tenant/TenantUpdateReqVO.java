package com.rc.cloud.app.system.vo.tenant.tenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 租户更新 Request VO
 */
@Schema(description = "管理后台 - 租户更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TenantUpdateReqVO extends TenantBaseVO {

    @Schema(description = "租户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "租户编号不能为空")
    private String id;

}
