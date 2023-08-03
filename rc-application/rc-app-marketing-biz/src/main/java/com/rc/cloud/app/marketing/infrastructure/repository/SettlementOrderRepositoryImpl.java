package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrder;
import com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrderRepository;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.SettlementOrderMapper;

import javax.annotation.Resource;

/**
 * @ClassName SettlementOrderRepositoryImpl
 * @Author liandy
 * @Date 2023/8/3 14:46
 * @Description TODO
 * @Version 1.0
 */
public class SettlementOrderRepositoryImpl implements SettlementOrderRepository {
    @Resource
    private SettlementOrderMapper settlementOrderMapper;

    @Override
    public boolean save(SettlementOrder settlementOrder) {
        return false;
    }
}
