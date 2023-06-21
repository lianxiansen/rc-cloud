package com.rc.cloud.app.mall.appearance.request;

import com.rc.cloud.app.mall.application.data.BaseQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AppApiVO extends BaseQuery {

    /*@NotNull(message = "请设置access_token")
    @Pattern(regexp = "^(JqekSfHq)$", message = "access_token错误")*/
    private String access_token;
}
