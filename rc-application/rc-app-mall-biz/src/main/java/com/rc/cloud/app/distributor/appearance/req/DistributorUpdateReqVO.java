package com.rc.cloud.app.distributor.appearance.req;

import com.rc.cloud.app.distributor.appearance.vo.DistributorBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Schema(description = "用户 APP - 经销商更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DistributorUpdateReqVO extends DistributorBaseVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3248")
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "经销商明细")
    private String distributorDetail;

    @Schema(description = "联系人")
    private List<DistributorContactUpdateReqVO> contacts;
}
