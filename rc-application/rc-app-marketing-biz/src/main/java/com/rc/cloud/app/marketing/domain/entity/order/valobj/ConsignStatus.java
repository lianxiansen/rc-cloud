package com.rc.cloud.app.marketing.domain.entity.order.valobj;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ConsignStatus
 * @Author liandy
 * @Date 2023/7/29 9:36
 * @Description  发货状态
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ConsignStatus {
    /**
     * 发货状态枚举
     */
    UNCONSIGN (0, "未发货"),
    CONSIGNED(1, "已发货"),
    RECEIVED(2, "已收货");


    /**
     * 值
     */
    private final int value;

    /**
     * 描述
     */
    private final String description;

    public static ConsignStatus valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue() == value, ConsignStatus.values());
    }
}
