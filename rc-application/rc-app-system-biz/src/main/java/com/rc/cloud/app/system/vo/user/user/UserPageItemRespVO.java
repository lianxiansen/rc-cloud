package com.rc.cloud.app.system.vo.user.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import static com.rc.cloud.common.core.util.date.DateFormat.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 用户分页时的信息 Response VO,相比用户基本信息来说，会多部门信息
 */
@Schema(description = "管理后台 - 用户分页时的信息 Response VO,相比用户基本信息来说，会多部门信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPageItemRespVO extends UserRespVO {

    /**
     * 所在部门
     */
    private Dept dept;

    @Schema(description = "部门")
    @Data
    public static class Dept {

        @Schema(description = "部门编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private String id;

        @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "研发部")
        private String name;

    }

    @Schema(description = "创建时间", example = "2022-07-01 00:00:00")
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = "GMT+8")
    private LocalDateTime createTime;
}
