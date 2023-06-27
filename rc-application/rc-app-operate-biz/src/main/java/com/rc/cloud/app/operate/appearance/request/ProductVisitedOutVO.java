package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @auther Ushop
 * @date 2021/4/16 8:41
 * @Description ProductVisitedOutVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductVisitedOutVO {
    @Positive(message = "请填写访出时间，时间戳(ms)格式！")
    private long out_time;
    @NotBlank(message = "请填写访问记录ID")
    private String product_visited_id;
}
