package com.rc.cloud.app.operate.application.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ProductGroupItemCreateDTO {
    @NotNull(message = "产品组合唯一标识")
    private String productGroupId;
    @NotNull(message = "产品唯一标识")
    private String productId;

}
