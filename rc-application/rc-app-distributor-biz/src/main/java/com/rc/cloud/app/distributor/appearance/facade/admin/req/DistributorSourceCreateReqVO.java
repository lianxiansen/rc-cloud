package com.rc.cloud.app.distributor.appearance.facade.admin.req;

import com.rc.cloud.app.distributor.appearance.vo.DistributorSourceBaseVO;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户 APP - 经销商来源创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DistributorSourceCreateReqVO extends DistributorSourceBaseVO {

}
