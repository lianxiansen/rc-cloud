package com.rc.cloud.app.product.application.data;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class NoDeliveryTemplateQueryII {
    @Min(value = 1, message = "请填写模板id")
    public int id;
    public int pid;
    @NotBlank(message = "ids不能为空")
    public String ids;
}
