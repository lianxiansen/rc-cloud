package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.service.SettleOrderDomainServcie;
import org.springframework.stereotype.Service;

/**
 * @ClassName SettleOrderDomainServcie
 * @Author liandy
 * @Date 2023/8/2 17:05
 * @Description TODO
 * @Version 1.0
 */
@Service
public class SettleOrderDomainServcieImpl implements SettleOrderDomainServcie {
    /**
     * 结算
     * @param transactionId 微信支付订单号
     * * @param bizPayNo 微信支付订单号
     */
    public void settle(String transactionId,String bizPayNo){

    }
}
