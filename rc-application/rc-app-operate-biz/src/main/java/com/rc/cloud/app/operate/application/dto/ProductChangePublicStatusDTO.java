package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改商品publicFlag请求数据")
public class ProductChangePublicStatusDTO {

    public String productId;


    @Schema(description = "是否公开")
    public boolean publicFlag;


}
