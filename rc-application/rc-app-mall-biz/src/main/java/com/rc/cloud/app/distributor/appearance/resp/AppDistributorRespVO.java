package com.rc.cloud.app.distributor.appearance.resp;

import com.rc.cloud.app.distributor.appearance.vo.AppDistributorBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "用户 APP - 经销商 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorRespVO extends AppDistributorBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3248")
    private Integer id;

}
