package com.rc.cloud.app.operate.application.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductSaveSpecKeyValuePairsVO {

    @NotBlank(message = "淘宝sku的规格项名称有遗漏")
    private String spec_name;

    @NotBlank(message = "淘宝sku的规格值名称有遗漏")
    private String spec_value;
}
