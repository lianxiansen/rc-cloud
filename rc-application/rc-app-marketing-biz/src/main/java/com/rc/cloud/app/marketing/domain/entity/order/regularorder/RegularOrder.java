package com.rc.cloud.app.marketing.domain.entity.order.regularorder;


import com.rc.cloud.app.marketing.domain.entity.order.regularorder.valobj.OrderAction;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.valobj.OrderStatus;
import com.rc.cloud.app.marketing.domain.entity.order.settlementorder.SettlementOrder;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.*;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName RegularOrder
 * @Author liandy
 * @Date 2023/7/28 16:57
 * @Description 常规订单
 * @Version 1.0
 */
public class RegularOrder {
    /**
     * 订单唯一标识
     */
    private String id;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 订单状态,0:等待卖家审核 1:等待买家付款 2:等待卖家发货 3:等待买家收货 4:交易完成
     */
    private OrderStatus orderStatus;


    /**
     * 商品金额=订单子项商品项金额总和
     */
    private BigDecimal productAmount;

    /**
     * 运费
     */
    private BigDecimal freightAmount;


    /**
     * 改价金额
     */
    private BigDecimal changeAmount;

    /**
     * 交易方式 0：扫码支付
     */
    private TradeType tradeType;


    /**
     * 付款状态 0:未付款 1：已付款
     */
    private PayStatus payStatus;
    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 发货状态,0:未发货，1：已发货，2：已收货
     */
    private ConsignStatus consignStatus;
    /**
     * 发货时间
     */
    private LocalDateTime consignTime;

    /**
     * 完成时间
     */
    private LocalDateTime endTime;
    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;

    /**
     * 买家
     */
    private Buyer buyer;
    /**
     * 收货信息
     */
    private Receiver receiver;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 交易号
     *
     * @see SettlementOrder tradeNo
     */
    private String tradeNo;


    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<RegularOrderLine> lines;

    public RegularOrder(String id, String orderNumber) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = OrderStatus.AUDITING;
        this.productAmount = BigDecimal.ZERO;
        this.freightAmount = BigDecimal.ZERO;
        this.tradeType =TradeType.ONLINE;
        this.payStatus = PayStatus.UNPAY;
        this.consignStatus = ConsignStatus.UNCONSIGN;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.lines = new ArrayList<>();
    }

    public void addLines(List<RegularOrderLine> lines) {
        lines.forEach(item -> {
            addLine(item);
        });
    }

    public void addLine(RegularOrderLine line) {
        this.productAmount = this.productAmount.add(line.getProductAmount());
        this.lines.add(line);
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public String getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * 获取订单需要支付的金额
     *
     * @return
     */
    public BigDecimal getPayAmount() {
        if (!Objects.isNull(this.changeAmount)) {
            return this.changeAmount;
        }
        return this.productAmount.add(this.freightAmount);
    }


    public int getProductQuantity() {
        return (int) (this.lines.stream().map(item -> item.getProduct()).distinct().count());
    }

    public int getProductItemQuantity() {
        return (int) this.lines.stream().map(item -> item.getProduct()).count();
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public String getRemark() {
        return remark;
    }

    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public LocalDateTime getConsignTime() {
        return consignTime;
    }

    public ConsignStatus getConsignStatus() {
        return consignStatus;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public List<RegularOrderLine> getLines() {
        return lines;
    }

    public void setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }


    /**
     * 订单支付
     *
     * @param actualPayAmount
     */
    public void pay(BigDecimal actualPayAmount) {
        if (canPay()) {
            if (this.getPayAmount().equals(actualPayAmount)) {
                this.orderStatus = OrderStatus.DELIVERING;
                this.payStatus = PayStatus.PAYED;
                return;
            }
            //TODO liandy:错误码
            throw new ServiceException(9999, "支付金额不匹配");
        }

    }

    private boolean canPay() {
        if (this.orderStatus == OrderStatus.AUDITING || this.orderStatus == OrderStatus.PAYING) {
            if (this.payStatus == PayStatus.UNPAY) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO liandy:统一错误码
     * 改价
     *
     * @param changeAmount
     */
    public void changeAmount(BigDecimal changeAmount) {
        AssertUtils.notNull(changeAmount, "changeAmount must be not null");
        if (unAudit()) {
            if (isChangeAmountBetweenZeroToPayAmount(changeAmount)) {
                this.changeAmount = changeAmount;
                return;
            }
            throw new ServiceException(9999, "改价金额在0至支付金额之间");
        } else {
            throw new ServiceException(9999, "卖家已审核才可改价");
        }
    }

    public void doActoin(OrderAction action) {
        this.orderStatus = action.doAction(this.getOrderStatus());
    }




    /**
     * 卖家未审核
     * @return true:已审核 false:未审核
     */
    private boolean unAudit() {
        if (this.orderStatus == OrderStatus.AUDITING) {
            return true;
        }
        return false;
    }






    /**
     * 改价金额是否在0至支付金额之间
     *
     * @return
     */
    private boolean isChangeAmountBetweenZeroToPayAmount(BigDecimal changeAmount) {
        if (changeAmount.compareTo(this.getPayAmount()) <= 0 && changeAmount.compareTo(BigDecimal.ZERO) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegularOrder that = (RegularOrder) o;
        return getTradeType() == that.getTradeType() && getId().equals(that.getId()) &&
                getOrderNumber().equals(that.getOrderNumber()) &&
                getOrderStatus() == that.getOrderStatus() &&
                getProductAmount().equals(that.getProductAmount()) &&
                getFreightAmount().equals(that.getFreightAmount()) &&
                Objects.equals(getChangeAmount(), that.getChangeAmount()) &&
                Objects.equals(getPayTime(), that.getPayTime()) &&
                getPayStatus() == that.getPayStatus() &&
                Objects.equals(getConsignTime(), that.getConsignTime()) &&
                getConsignStatus() == that.getConsignStatus() &&
                Objects.equals(getEndTime(), that.getEndTime()) &&
                Objects.equals(getCloseTime(), that.getCloseTime()) &&
                getBuyer().equals(that.getBuyer()) &&
                getReceiver().equals(that.getReceiver()) &&
                Objects.equals(getRemark(), that.getRemark()) &&
                getTradeNo().equals(that.getTradeNo()) &&
                getCreateTime().equals(that.getCreateTime()) &&
                Objects.equals(getUpdateTime(), that.getUpdateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderNumber(), getOrderStatus(), getProductAmount(), getFreightAmount(), getChangeAmount(), getTradeType(), getPayTime(), getPayStatus(), getConsignTime(), getConsignStatus(), getEndTime(), getCloseTime(), getBuyer(), getReceiver(), getRemark(), getTradeNo(), getCreateTime(), getUpdateTime());
    }
}

