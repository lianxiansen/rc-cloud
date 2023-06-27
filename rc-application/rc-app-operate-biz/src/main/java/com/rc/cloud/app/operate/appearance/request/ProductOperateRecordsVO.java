package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ProductOperateRecordsVO extends PageVO {

    @Min(value = 1, message = "pid不支持(范围：不小于1)")
    private int pid;

    private String keywords;
}
