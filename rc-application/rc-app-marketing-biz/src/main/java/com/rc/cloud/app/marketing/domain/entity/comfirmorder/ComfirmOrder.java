package com.rc.cloud.app.marketing.domain.entity.comfirmorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PlaceOrder
 * @Author liandy
 * @Date 2023/7/28 13:39
 * @Description 确认订单
 * @Version 1.0
 */
public class ComfirmOrder {
    private String id;
    private String deliveryAddressId;


    private List<ComfirmOrderItem> items;
    /**
     * 交易方式 0：扫码支付
     */
    private int payType;

    /**
     * 配送方式 0：托运自提 1：快递配送 2：送货上门
     */
    private DeliveryType deliveryType;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 商品金额
     */
    private BigDecimal productAmout;

    /**
     * 运费金额
     */
    private BigDecimal freightAmount;

    /**
     * 应付总额
     */
    private BigDecimal payAmount;
    public ComfirmOrder(String id) {
        this.id=id;
        this.payType=0;
        this.deliveryType=DeliveryType.CONSIGN;
        this.productAmout=BigDecimal.ZERO;
        this.freightAmount=BigDecimal.ZERO;
        this.payAmount=BigDecimal.ZERO;
        this.items=new ArrayList<>();
    }

    public void changePayType() {
    }


    public String getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public List<ComfirmOrderItem> getItems() {
        return items;
    }

    public int getPayType() {
        return payType;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public String getNote() {
        return note;
    }

    public BigDecimal getProductAmout() {
        return productAmout;
    }

    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setDeliveryAddressId(String deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getId() {
        return this.id;
    }

    public void addItem(ComfirmOrderItem item){
        this.productAmout=this.productAmout.add(item.getProductItem().getAmount());
        this.payAmount=this.productAmout.add(this.freightAmount);
        this.items.add(item);
    }

    public void changeDeliveryType(DeliveryType deliveryType){
        this.deliveryType = deliveryType;
    }

    public void modifyNote(String note){
        this.note = note;
    }


}
