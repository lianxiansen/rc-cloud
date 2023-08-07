package com.rc.cloud.app.marketing.domain.entity.cart;

/**
 * @author WJF
 * @create 2023-08-07 13:26
 * @description TODO
 */

public enum ExpireState {
    NOT_EXPIRE(0, "未过期"),
    PRODUCT_EXPIRE(1, "商品过期"),
    ACTIVITY_EXPIRE(2, "活动过期");

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Integer code;
    String message;

    ExpireState(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
