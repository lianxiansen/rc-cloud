package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Schema(description = "修改商品上架状态请求数据")
public class ProductChangeOnshelfStatusDTO {

    public String productId;


    @Min(value = 0, message = "上架状态值为0,1,2")
    @Max(value = 2, message = "上架状态值为0,1,2")
    @Schema(description = "上架状态")
    public int onshelfStatus;


}
