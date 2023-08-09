package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改商品newFlag请求数据")
public class ProductChangeNewStatusDTO {

    public String productId;

    @Schema(description = "是否是新品")
    public boolean newFlag;


}
