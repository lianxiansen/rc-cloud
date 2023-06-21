package com.rc.cloud.app.product.distributor.controller.app.source.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 经销商来源创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppDistributorSourceCreateReqVO extends AppDistributorSourceBaseVO {

}
