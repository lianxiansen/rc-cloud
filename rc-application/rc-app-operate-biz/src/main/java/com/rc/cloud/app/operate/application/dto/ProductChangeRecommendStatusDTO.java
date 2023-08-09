package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改商品recommendFlag请求数据")
public class ProductChangeRecommendStatusDTO {

    public String productId;

    @Schema(description = "是否是首页推荐，设置为true后会展示的app首页，ffcat应用")
    public boolean recommendFlag;


}
