package com.rc.cloud.app.distributor.appearance.resp;

import com.rc.cloud.app.distributor.appearance.vo.AppDistributorChannelBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "用户 APP - 经销商渠道 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorChannelRespVO extends AppDistributorChannelBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7726")
    private Integer id;

}