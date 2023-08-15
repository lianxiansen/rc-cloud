package com.rc.cloud.app.marketing.domain.entity.order.valobj;

import java.math.BigDecimal;

/**
 * @ClassName AbstractOrder
 * @Author liandy
 * @Date 2023/8/14 13:02
 * @Description
 * @Version 1.0
 */
public class OrderLine {
    /**
     * 订单项标识
     */
    private String id;
    /**
     * 商品
     */
    protected Product product;
    /**
     * 商品数量
     */
    protected ProductQuality productQuality;


    protected BigDecimal productAmount;

    public OrderLine(String id, Product product, ProductQuality quality){
        this.id=id;
        this.product = product;
        this.productQuality =quality;
        resetProductAmount();
    }

    public void resetProductAmount() {
        this.productAmount=calculateProductAmount();
    }


    public BigDecimal calculateProductAmount(){
        return this.product.getProductPrice().multiply(new BigDecimal(productQuality.getValue()));
    }

    public Product getProduct() {
        return product;
    }

    public ProductQuality getProductQuality() {
        return productQuality;
    }
    public String getId() {
        return id;
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }
}
