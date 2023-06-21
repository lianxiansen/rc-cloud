package com.rc.cloud.app.product.distributor.controller.app.level.vo;

import com.rc.cloud.common.core.pojo.PageParam;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import static com.rc.cloud.common.core.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 经销商客户等级分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorLevelPageReqVO extends PageParam {

    @Schema(description = "客户等级", example = "张三")
    private String name;

    @Schema(description = "说明")
    private String explain;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] createtime;

}
