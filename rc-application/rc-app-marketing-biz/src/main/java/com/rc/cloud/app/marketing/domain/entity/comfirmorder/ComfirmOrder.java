package com.rc.cloud.app.marketing.domain.entity.comfirmorder;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName PlaceOrder
 * @Author liandy
 * @Date 2023/7/28 13:39
 * @Description 确认订单
 * @Version 1.0
 */
public class ComfirmOrder {
    private String id;
    private DeliveryAddress deliveryAddress;
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
        this.id = id;
        this.payType = 0;
        this.deliveryType = DeliveryType.CONSIGN;
        this.productAmout = BigDecimal.ZERO;
        this.freightAmount = BigDecimal.ZERO;
        this.payAmount = BigDecimal.ZERO;
        this.items = new ArrayList<>();
    }

    public void changePayType() {
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


    public String getId() {
        return this.id;
    }

    public void addItem(ComfirmOrderItem item) {
        this.productAmout = this.productAmout.add(item.getProductItem().getProductItemAmount());
        this.payAmount = this.productAmout.add(this.freightAmount);
        this.items.add(item);
    }

    public void changeDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void modifyNote(String note) {
        this.note = note;
    }

    public List<Product> getProducts() {
        return this.items.stream().map(p -> p.getProduct()).collect(Collectors.toList());
    }

    public List<ComfirmOrderItem> getItems(Product product){
        return this.items.stream().filter(item->item.getProduct().equals(product)).collect(Collectors.toList());
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
        this.payAmount = this.productAmout.add(this.freightAmount);
    }
}
