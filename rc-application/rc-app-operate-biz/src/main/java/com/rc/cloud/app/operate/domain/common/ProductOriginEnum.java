package com.rc.cloud.app.operate.domain.common;

/**
 * @auther chenjianxiang
 * @date 2021/4/1 14:12
 * @Description 商品来源，这里指商品的出处，第三方或者自营
 * @PROJECT_NAME
 */
public enum ProductOriginEnum {
    Self(0, "自营");

    public final Integer value;
    public final String name;

    ProductOriginEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

}
