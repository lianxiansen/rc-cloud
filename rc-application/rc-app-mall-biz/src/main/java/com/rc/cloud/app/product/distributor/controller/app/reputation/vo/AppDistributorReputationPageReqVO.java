package com.rc.cloud.app.product.distributor.controller.app.reputation.vo;

import com.rc.cloud.common.core.pojo.PageParam;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import static com.rc.cloud.common.core.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 经销商客户信誉分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorReputationPageReqVO extends PageParam {

    @Schema(description = "信誉等级", example = "赵六")
    private String name;

    @Schema(description = "说明")
    private String explain;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] createtime;

}
