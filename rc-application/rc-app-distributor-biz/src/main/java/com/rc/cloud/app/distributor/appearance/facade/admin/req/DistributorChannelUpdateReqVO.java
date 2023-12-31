package com.rc.cloud.app.distributor.appearance.facade.admin.req;

import com.rc.cloud.app.distributor.appearance.vo.DistributorChannelBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 经销商渠道更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DistributorChannelUpdateReqVO extends DistributorChannelBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7726")
    @NotNull(message = "id不能为空")
    private String id;

}
