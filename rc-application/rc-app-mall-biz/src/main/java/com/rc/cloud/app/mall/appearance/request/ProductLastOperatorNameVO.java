package com.rc.cloud.app.mall.appearance.request;

import com.qxun.qlive.common.base.AppApiVO;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ProductLastOperatorNameVO extends AppApiVO {

    @Min(value = 1, message = "请设置id(范围：不小于1)")
    private int id;
}
