package com.rc.cloud.app.system.vo.tenant.tenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import static com.rc.cloud.common.core.util.date.DateFormat.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 租户 Excel 导出 Request VO,参数和 TenantPageReqVO 是一致的
 */
@Schema(description = "管理后台 - 租户 Excel 导出 Request VO,参数和 TenantPageReqVO 是一致的")
@Data
public class TenantExportReqVO {

    @Schema(description = "租户名", example = "RC")
    private String name;

    @Schema(description = "联系人", example = "RC")
    private String contactName;

    @Schema(description = "联系手机", example = "15601691377")
    private String contactMobile;

    @Schema(description = "租户状态（0正常 1停用）", example = "1")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
