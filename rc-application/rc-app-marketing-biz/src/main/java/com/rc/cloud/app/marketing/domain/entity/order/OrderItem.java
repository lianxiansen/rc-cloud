package com.rc.cloud.app.marketing.domain.entity.order;

import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;

import java.math.BigDecimal;

/**
 * @ClassName OrderItem
 * @Author liandy
 * @Date 2023/7/28 17:25
 * @Description TODO
 * @Version 1.0
 */
public class OrderItem {
    /**
     * 订单项标识
     */
    private String id;
    /**
     * 订单标识
     */
    private String orderId;




    private ProductItem productItem;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    public OrderItem(String id, String orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    /**
     * 商品总金额
     */
    public BigDecimal getProductItemAmount(){
        return new BigDecimal(productItem.getNum()).multiply(productItem.getPrice());
    }


    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public ProductItem getProductItem() {
        return productItem;
    }
}

