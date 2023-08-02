package com.rc.cloud.app.marketing.domain.entity.settlementorder;

import com.rc.cloud.app.marketing.domain.entity.common.PayStatus;
import com.rc.cloud.app.marketing.domain.entity.common.SettledEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName OrderSettlement
 * @Author liandy
 * @Date 2023/8/2 09:29
 * @Description 结算订单
 * @Version 1.0
 */
public class SettlementOrder implements Serializable {
    /**
     * 支付结算单据ID
     */
    private String id;

    /**
     * 系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
     */
    private String tradeNo;

    /**
     * 外部订单流水号，比如：微信支付系统生成的订单号
     */
    private String outTradeNo;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */

    private int payType;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 用户ID
     */

    private String buyerId;



    /**
     * 创建时间
     */

    private LocalDateTime createTime;
    /**
     * 是否清算 0:否 1:是
     */

    private SettledEnum settled;
    /**
     * 清算时间
     */

    private LocalDateTime settledTime;

    /**
     * 支付状态
     */
    private PayStatus payStatus;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public SettledEnum getSettled() {
        return settled;
    }

    public void setSettled(SettledEnum settled) {
        this.settled = settled;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getSettledTime() {
        return settledTime;
    }

    public void setSettledTime(LocalDateTime settledTime) {
        this.settledTime = settledTime;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

}
