package com.rc.cloud.app.marketing.domain.entity.order.valobj;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName TradeType
 * @Author liandy
 * @Date 2023/7/29 9:37
 * @Description  交易方式
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum TradeType {
    /**
     * 交易方式枚举
     */
    ONLINE(0, "在线交易", "配送到托运站，自提，托运费用自付"),
    OFFLINE(1, "线下支付", "发送快递，运费需按快递费用先支付"),
    ACCOUNT_PERIOD(2, "账期交易", "送货上门，费用自理");


    /**
     * 关键字
     */
    private final int code;
    /**
     * 名称
     */
    private final String name;
    /**
     * 描述
     */
    private final String description;

    public static TradeType valueOf(Integer value) {
        return ArrayUtil.firstMatch(tradeType -> tradeType.getCode() == value, TradeType.values());
    }
}
