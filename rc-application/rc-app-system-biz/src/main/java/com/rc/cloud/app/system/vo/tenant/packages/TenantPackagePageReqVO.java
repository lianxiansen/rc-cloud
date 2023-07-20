package com.rc.cloud.app.system.vo.tenant.packages;

import com.rc.cloud.common.core.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.rc.cloud.common.core.util.date.DateFormat.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 租户套餐分页 Request VO
 */
@Schema(description = "管理后台 - 租户套餐分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TenantPackagePageReqVO extends PageParam {

    @Schema(description = "套餐名", example = "VIP")
    private String name;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "好")
    private String remark;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;
}
