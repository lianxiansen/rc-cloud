package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "移除商品进入回收站")
public class ProductChangeRecycleStatusDTO {

    public String productId;

    @Schema(description = "是否进入回收站，true：将商品放入回收站，false：还原商品")
    public boolean recycleFlag;


}
