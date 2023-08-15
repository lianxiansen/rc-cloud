package com.rc.cloud.app.marketing.domain.entity.order.comfirmorder;

import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.TradeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ComfirmOrder
 * @Author liandy
 * @Date 2023/7/28 13:39
 * @Description 确认订单
 * @Version 1.0
 */
public class ComfirmOrder {
    private String id;
    private List<ComfirmOrderLine> lines;
    /**
     * 交易方式
     */
    private TradeType tradeType;

    /**
     * 配送方式
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
        this.tradeType = TradeType.ONLINE;
        this.deliveryType = DeliveryType.CONSIGN;
        this.productAmout = BigDecimal.ZERO;
        this.freightAmount = BigDecimal.ZERO;
        this.payAmount = BigDecimal.ZERO;
        this.lines = new ArrayList<>();
    }

    public void changePayType() {
    }



    public List<ComfirmOrderLine> getLines() {
        return lines;
    }

    public TradeType getTradeType() {
        return tradeType;
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


    public void addLines(List<ComfirmOrderLine> lines) {
        lines.forEach(item->{
            addLine(item);
        });
    }

    public void addLine(ComfirmOrderLine line) {
        this.productAmout = this.productAmout.add(line.getProductAmount());
        this.payAmount = this.productAmout.add(this.freightAmount);
        this.lines.add(line);
    }

    public void changeDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void modifyNote(String note) {
        this.note = note;
    }

    public List<Product> getProducts() {
        return this.lines.stream().map(p -> p.getProduct()).collect(Collectors.toList());
    }



    public void changeTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }



    public void setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
        this.payAmount = this.productAmout.add(this.freightAmount);
    }


}
