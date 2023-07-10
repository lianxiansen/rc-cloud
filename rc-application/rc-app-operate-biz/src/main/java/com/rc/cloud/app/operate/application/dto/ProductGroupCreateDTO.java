package com.rc.cloud.app.operate.application.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
@Data
@Accessors(chain = true)
public class ProductGroupCreateDTO {
    @NotNull(message = "产品唯一标识不能为空")
    private String productId;
    @NotNull(message = "组合名称不能为空")
    private String name;

}
