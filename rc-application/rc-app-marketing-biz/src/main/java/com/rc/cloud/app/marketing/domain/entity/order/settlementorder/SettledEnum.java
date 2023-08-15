package com.rc.cloud.app.marketing.domain.entity.order.settlementorder;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName YesNoEnum
 * @Author liandy
 * @Date 2023/7/29 9:36
 * @Description  结清状态枚举
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum SettledEnum {

    /**
     *
     */
    NO (0),
    YES(1);


    /**
     * 值
     */
    private final int value;



    public static SettledEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue() == value, SettledEnum.values());
    }
}
