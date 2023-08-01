package com.rc.cloud.app.marketing.domain.comfirmorder;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName DeliveryType
 * @Author liandy
 * @Date 2023/7/29 9:37
 * @Description  配送方式
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum DeliveryType {
    /**
     * 配送方式枚举
     */
    CONSIGN(0, "托运自提", "配送到托运站，自提，托运费用自付"),
    EXPRESS(1, "快递配送", "发送快递，运费需按快递费用先支付"),
    HOME(2, "送货上门", "送货上门，费用自理");



    /**
     * 关键字
     */
    private final int key;
    /**
     * 名称
     */
    private final String name;
    /**
     * 描述
     */
    private final String description;

    public static DeliveryType valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getKey() == value, DeliveryType.values());
    }
}
