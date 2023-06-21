package com.rc.cloud.app.mall.appearance.request;

import com.qxun.qlive.common.base.PageVO;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ProductOperateRecordsVO extends PageVO {

    @Min(value = 1, message = "pid不支持(范围：不小于1)")
    private int pid;

    private String keywords;
}
