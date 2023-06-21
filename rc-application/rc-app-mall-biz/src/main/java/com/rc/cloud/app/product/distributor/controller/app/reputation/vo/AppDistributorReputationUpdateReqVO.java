package com.rc.cloud.app.product.distributor.controller.app.reputation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 经销商客户信誉更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorReputationUpdateReqVO extends AppDistributorReputationBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29766")
    @NotNull(message = "id不能为空")
    private Integer id;

}
