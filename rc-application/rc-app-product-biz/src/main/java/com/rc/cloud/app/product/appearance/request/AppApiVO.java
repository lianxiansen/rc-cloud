package com.rc.cloud.app.product.appearance.request;

import com.rc.cloud.app.product.application.data.BaseQuery;
import lombok.Data;

@Data
public class AppApiVO extends BaseQuery {

    /*@NotNull(message = "请设置access_token")
    @Pattern(regexp = "^(JqekSfHq)$", message = "access_token错误")*/
    private String access_token;
}
