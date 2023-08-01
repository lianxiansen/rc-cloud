package com.rc.cloud.app.marketing.domain.order;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Order
 * @Author liandy
 * @Date 2023/7/28 16:57
 * @Description TODO
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
    private String orderNo;
    /**
     * 订单状态,0:等待卖家审核 1:等待买家付款 2:等待卖家发货 3:等待买家收货 4:交易完成
     */
    private OrderStatus orderStatus;


    /**
     * 金额合计
     */
    private BigDecimal totalAmount;
    /**
     * 数量合计
     */
    private int totalNum;
    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;
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
     * 交易完成时间
     */
    private LocalDateTime endTime;
    /**
     * 交易关闭时间
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
     * 交易流水号
     */
    private String transactionId;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<OrderItem> items;

    public Order(String id,String orderNo){
        this.id=id;
        this.orderNo = orderNo;
        this.orderStatus=OrderStatus.AUDITING;
        totalAmount=BigDecimal.ZERO;
        totalNum=0;
        freightAmount=BigDecimal.ZERO;
        payAmount =BigDecimal.ZERO;
        payType=0;
        payStatus=PayStatus.UNPAY;
        consignStatus=ConsignStatus.UNCONSIGN;
        createTime=LocalDateTime.now();
        updateTime=LocalDateTime.now();
        items=new ArrayList<>();
    }
    public void addItem(OrderItem item){
        this.items.add(item);
        this.totalNum+=item.getProductItem().getNum();
        this.totalAmount=this.totalAmount.add(item.getProductItem().getAmount());
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

    public String getOrderNo() {
        return orderNo;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
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

    public String getTransactionId() {
        return transactionId;
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

    /**
     * 计算应付金额
     * @return
     */
    public BigDecimal calculateShoudPayAmount(){
        return this.totalAmount.add(this.freightAmount);
    }
}

