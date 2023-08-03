package com.rc.cloud.app.marketing.domain.entity.settlementorder;

/**
 * @Interfaces SettlementOrderRepository
 * @Author liandy
 * @Date 2023/8/3 14:39
 * @Description  结算订单资源库
 * @Version 1.0
 */
public interface SettlementOrderRepository {
    /**
     * 保存
     * @param settlementOrder
     * @return
     */
    boolean save(SettlementOrder settlementOrder);



}
