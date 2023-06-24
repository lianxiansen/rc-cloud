package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductSaveFavorableRateVO {

    @NotNull(message = "请设置id")
    @Min(value = 1, message = "id不支持(范围：不小于1)")
    private Integer id;

    @NotNull(message = "请设置rate")
    @Min(value = 0, message = "rate不支持(范围：0-100)")
    @Max(value = 100, message = "rate不支持(范围：0-100)")
    private Integer rate;
}
