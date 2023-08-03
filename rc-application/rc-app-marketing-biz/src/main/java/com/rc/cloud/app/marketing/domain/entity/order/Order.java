package com.rc.cloud.app.marketing.domain.entity.order;


import com.rc.cloud.app.marketing.domain.entity.common.PayStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Order
 * @Author liandy
 * @Date 2023/7/28 16:57
 * @Description 订单
 * @Version 1.0
 */
public class Order {
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
     * 商品数量合计
     */
    private int productQuantity;
    /**
     * 商品项数量合计
     */
    private int productItemQuantity;


    /**
     * 商品金额
     */
    private BigDecimal productAmount;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 要支付的金额=商品金额+运费
     */
    private BigDecimal payAmount;

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
     * @see com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrder tradeNo
     */
    private String tradeNo;


    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<OrderItem> items;

    public Order(String id, String orderNo, String transactionId) {
        this.id = id;
        this.orderNumber = orderNo;
        this.orderStatus = OrderStatus.AUDITING;
        payAmount = BigDecimal.ZERO;
        productItemQuantity = 0;
        freightAmount = BigDecimal.ZERO;
        changeAmount = BigDecimal.ZERO;
        payType = 0;
        payStatus = PayStatus.UNPAY;
        consignStatus = ConsignStatus.UNCONSIGN;
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
        items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        this.productItemQuantity += item.getProductItem().getProductItemQuantity();
        this.payAmount = this.payAmount.add(item.getProductItem().getProductItemAmount());
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

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public int getProductItemQuantity() {
        return productItemQuantity;
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

    public List<OrderItem> getItems() {
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
     * 计算应付金额
     *
     * @return
     */
    public BigDecimal calculateShoudPayAmount() {
        return this.payAmount.add(this.freightAmount);
    }


    /**
     * 订单支付
     *
     * @param payAmount
     */
    public void pay(BigDecimal payAmount) {
        if (canPay()) {
            this.changeAmount = payAmount;
            this.orderStatus = OrderStatus.DELIVERING;
            this.payStatus = PayStatus.PAYED;
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


}

