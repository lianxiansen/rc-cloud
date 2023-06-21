package com.rc.cloud.app.mall.appearance.request;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

/**
 * @auther Ushop
 * @date 2021/4/15 16:28
 * @Description ProductVisitedInVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductVisitedInVO {
    @PositiveOrZero(message = "类型不能是负数")
    private int product_type;
    @PositiveOrZero(message = "商品ID不能是负数")
    private int pid;
    @PositiveOrZero(message = "抢购ID不能是负数")
    private int sk_pid;
    @PositiveOrZero(message = "拼团ID不能是负数")
    private int gp_pid;
    @PositiveOrZero(message = "时间戳不能是负数")
    private long ent_time;
    @PositiveOrZero(message = "referer的枚举不能是负数")
    private int referer = 0;
}
