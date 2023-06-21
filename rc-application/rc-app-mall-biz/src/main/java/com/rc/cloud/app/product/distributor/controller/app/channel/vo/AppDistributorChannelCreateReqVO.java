package com.rc.cloud.app.product.distributor.controller.app.channel.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 经销商渠道创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorChannelCreateReqVO extends AppDistributorChannelBaseVO {

}
