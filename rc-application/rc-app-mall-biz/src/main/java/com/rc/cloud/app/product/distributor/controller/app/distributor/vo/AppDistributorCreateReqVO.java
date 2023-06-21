package com.rc.cloud.app.product.distributor.controller.app.distributor.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 经销商创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorCreateReqVO extends AppDistributorBaseVO {

}
