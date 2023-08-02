package com.rc.cloud.app.marketing.domain.entity.order;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName PayStatus
 * @Author liandy
 * @Date 2023/7/29 9:36
 * @Description  付款状态
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum PayStatus {
    /**
     * 付款状态枚举
     */
    UNPAY (0, "未付款"),
    PAYED(1, "已付款");


    /**
     * 值
     */
    private final int value;

    /**
     * 描述
     */
    private final String description;

    public static PayStatus valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue() == value, PayStatus.values());
    }
}
