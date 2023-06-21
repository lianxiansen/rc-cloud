package com.rc.cloud.app.mall.application.data;

/**
 * @auther Ushop
 * @date 2021/3/24 17:36
 * @Description ProductShelfEnum
 * @PROJECT_NAME qyy-live
 */
public enum ProductShelfEnum {
    InitShelf(0, "未上架"),
    OnShelf(1, "上架"),
    OffShelf(2, "已下架");

    public final Integer value;
    public final String name;

    ProductShelfEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
