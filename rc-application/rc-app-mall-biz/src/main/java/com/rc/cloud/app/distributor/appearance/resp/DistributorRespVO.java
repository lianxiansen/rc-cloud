package com.rc.cloud.app.distributor.appearance.resp;

import com.rc.cloud.app.distributor.appearance.vo.DistributorBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 经销商 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DistributorRespVO extends DistributorBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3248")
    private Long id;

    @Schema(description = "联系人")
    private String contact;

    @Schema(description = "联系手机")
    private String mobile;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "修改人")
    private String updater;
}
