package com.rc.cloud.app.distributor.appearance.facade.admin.req;

import com.rc.cloud.common.core.pojo.PageParam;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.rc.cloud.common.core.util.date.DateFormat.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


@Schema(description = "用户 APP - 经销商渠道分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DistributorChannelPageReqVO extends PageParam {

    @Schema(description = "渠道名称", example = "李四")
    private String name;

    @Schema(description = "说明")
    private String description;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
