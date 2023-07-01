package com.rc.cloud.app.distributor.appearance.req;

import com.rc.cloud.app.distributor.appearance.vo.AppDistributorBaseVO;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "用户 APP - 经销商创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorCreateReqVO extends AppDistributorBaseVO {

    @Schema(description = "经销商明细")
    private String distributorDetail;

    @Schema(description = "联系人")
    private List<AppDistributorContactCreateReqVO> contacts;
}
