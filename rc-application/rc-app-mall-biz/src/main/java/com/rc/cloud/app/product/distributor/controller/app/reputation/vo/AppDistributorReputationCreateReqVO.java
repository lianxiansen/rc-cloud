package com.rc.cloud.app.product.distributor.controller.app.reputation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 经销商客户信誉创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorReputationCreateReqVO extends AppDistributorReputationBaseVO {

}
