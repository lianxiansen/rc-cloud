package com.rc.cloud.app.distributor.appearance.resp;

import com.rc.cloud.app.distributor.appearance.vo.AppDistributorSourceBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "用户 APP - 经销商来源 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorSourceRespVO extends AppDistributorSourceBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8314")
    private Integer id;

}
