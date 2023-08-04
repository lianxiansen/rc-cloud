package com.rc.cloud.app.marketing.domain.entity.regularorder.valobj;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @ClassName OrderStatus
 * @Author liandy
 * @Date 2023/7/29 9:36
 * @Description  订单状态
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {
    /**
     * 订单状态枚举
     */
    AUDITING (0, "等待卖家审核"),
    PAYING(1, "等待买家付款"),
    DELIVERING(2, "等待卖家发货"),
    RECEIVING(3, "等待买家收货"),
    CLOSE(4, "交易完成");


    /**
     * 值
     */
    private final int value;

    /**
     * 描述
     */
    private final String description;

    public static OrderStatus valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue() == value, OrderStatus.values());
    }
}
