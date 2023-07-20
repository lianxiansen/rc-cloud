package com.rc.cloud.app.distributor.appearance.facade.admin.req;

import com.rc.cloud.app.distributor.appearance.vo.DistributorBaseVO;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "用户 APP - 经销商创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DistributorCreateReqVO extends DistributorBaseVO {

    @Schema(description = "经销商明细")
    private String distributorDetail;

    @Schema(description = "联系人")
    private List<DistributorContactCreateReqVO> contacts;
}
