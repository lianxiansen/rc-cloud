package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

/**
 * @auther Ushop
 * @date 2021/3/28 13:25
 * @Description ProductCategoryVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductCategoryVO {
    @PositiveOrZero(message = "pid应大于等于0")
    private int pid;
    //父级类别ID，大于等于0，如0时默认为拿第一层级的类别
    @PositiveOrZero(message = "parentid应大于等于0")
    private int parentid;
    //是否显示隐藏项，为null时显示全部
    private Boolean islock;
    //是否排除该类别ID
    private int id;
    //层级
    private int layer;
    //Contains Title
    private String keyword;

    public ProductCategoryVO() {
        pid = 0;
        layer = 0;
        id = 0;
        parentid=0;
    }

}
