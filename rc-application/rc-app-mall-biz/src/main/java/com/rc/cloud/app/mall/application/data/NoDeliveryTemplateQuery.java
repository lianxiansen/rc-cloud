package com.rc.cloud.app.mall.application.data;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class NoDeliveryTemplateQuery extends BaseQuery {
    @Min(value = 1, message = "请填写模板id")
    public int id;

}
