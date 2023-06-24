package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class ProductSaveProductSpecsVO {

    @Min(value = 0, message = "spec_id不支持(范围：不小于0)")
    private int spec_id;

    @NotBlank(message = "spec_name不能为空")
    private String spec_name;

    private int spec_sort;

    @Valid
    @NotEmpty(message = "spec_values集合不能为空")
    private List<ProductSaveProductSpecValuesVO> spec_values;
}
