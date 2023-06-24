package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

/**
 * @auther Ushop
 * @date 2021/4/14 17:11
 * @Description ProductToShareVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductToShareVO {
    @Min(value = -1, message = "product_type数值错误")
    @Max(value = 2, message = "product_type数值错误")
    private int product_type;
    @PositiveOrZero(message = "商品ID不能是负数")
    private int pid;
    @PositiveOrZero(message = "抢购ID不能是负数")
    private int sk_pid;
    @PositiveOrZero(message = "拼团ID不能是负数")
    private int gp_pid;
    public ProductToShareVO() {
        product_type = -1;
    }
}

