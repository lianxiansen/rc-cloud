package com.rc.cloud.app.marketing.domain.entity.regularorder;


import com.rc.cloud.app.marketing.domain.entity.common.PayStatus;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Buyer;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.ConsignStatus;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.OrderStatus;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Receiver;
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
     * 商品金额=订单子项商品项金额=订单子项商品项数量*单价
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
    private int payType;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;
    /**
     * 付款状态 0:未付款 1：已付款
     */
    private PayStatus payStatus;

    /**
     * 发货时间
     */
    private LocalDateTime consignTime;
    /**
     * 发货状态,0:未发货，1：已发货，2：已收货
     */
    private ConsignStatus consignStatus;
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
     * @see com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrder tradeNo
     */
    private String tradeNo;


    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<RegularOrderItem> items;

    public RegularOrder(String id, String orderNo) {
        this.id = id;
        this.orderNumber = orderNo;
        this.orderStatus = OrderStatus.AUDITING;
        this.productAmount = BigDecimal.ZERO;
        this.freightAmount = BigDecimal.ZERO;
        this.payType = 0;
        this.payStatus = PayStatus.UNPAY;
        this.consignStatus = ConsignStatus.UNCONSIGN;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.items = new ArrayList<>();
    }
    public void addItems(List<RegularOrderItem> items) {
        items.forEach(item->{
            addItem(item);
        });
    }
    public void addItem(RegularOrderItem item) {
        this.productAmount = this.productAmount.add(item.getProduct().getProductAmount());
        this.items.add(item);
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
     * @return
     */
    public BigDecimal getPayAmount() {
        if(!Objects.isNull(this.changeAmount)){
            return this.changeAmount;
        }
        return this.productAmount.add(this.freightAmount);
    }


    public int getProductQuantity() {
        return (int) (this.items.stream().map(item -> item.getProduct()).distinct().count());
    }

    public int getProductItemQuantity() {
        return (int) this.items.stream().map(item -> item.getProduct()).count();
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

    public int getPayType() {
        return payType;
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

    public List<RegularOrderItem> getItems() {
        return items;
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
            if(this.getPayAmount().equals(actualPayAmount)){
                this.orderStatus = OrderStatus.DELIVERING;
                this.payStatus = PayStatus.PAYED;
                return ;
            }
            //TODO liandy:错误码
            throw new ServiceException(9999,"支付金额不匹配");
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

    /** TODO liandy:统一错误码
     * 改价
     * @param changeAmount
     */
    public void changeAmount(BigDecimal changeAmount) {
        AssertUtils.notNull(changeAmount,"changeAmount must be not null");
        if(unAudit()){
            if(isChangeAmountBetweenZeroToPayAmount(changeAmount)){
                this.changeAmount = changeAmount;
                return ;
            }
            throw new ServiceException(9999,"改价金额在0至支付金额之间");
        }else{
            throw new ServiceException(9999,"卖家已审核才可改价");
        }
    }

    /**
     * 改价金额是否在0至支付金额之间
     * @return
     */
    private boolean isChangeAmountBetweenZeroToPayAmount(BigDecimal changeAmount){
        if(changeAmount.compareTo(this.getPayAmount())<=0&&changeAmount.compareTo(BigDecimal.ZERO)>=0){
            return true;
        }
        return false;
    }



    /**
     * 商品审核
     */
    public void audit() {
        if(unAudit()){
            this.orderStatus=OrderStatus.PAYING;
        }
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
}

