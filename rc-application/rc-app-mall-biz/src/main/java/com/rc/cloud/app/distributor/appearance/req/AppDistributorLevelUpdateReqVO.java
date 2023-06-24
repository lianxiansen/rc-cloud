package com.rc.cloud.app.distributor.appearance.req;

import com.rc.cloud.app.distributor.appearance.vo.AppDistributorLevelBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "用户 APP - 经销商客户等级更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorLevelUpdateReqVO extends AppDistributorLevelBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26972")
    @NotNull(message = "id不能为空")
    private Integer id;

}