package com.rc.cloud.app.operate.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改商品类目状态请求数据")
public class ProductCategoryChangeEnableStatusDTO {

    public String productCategoryId;

    @Schema(description = "类目启用状态")
    public boolean enableFlag;

}
