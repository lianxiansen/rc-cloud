package com.rc.cloud.app.distributor.appearance.req;

import com.rc.cloud.app.distributor.appearance.vo.AppDistributorChannelBaseVO;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户 APP - 经销商渠道创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorChannelCreateReqVO extends AppDistributorChannelBaseVO {

}
