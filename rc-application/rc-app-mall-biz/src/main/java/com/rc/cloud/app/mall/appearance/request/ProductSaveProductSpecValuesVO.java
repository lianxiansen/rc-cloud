package com.rc.cloud.app.mall.appearance.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ProductSaveProductSpecValuesVO {

    @Min(value = 0, message = "spec_vid不支持(范围：不小于0)")
    private int spec_vid;

    @NotBlank(message = "spec_vname不能为空")
    private String spec_vname;

    private int spec_vsort;
}
