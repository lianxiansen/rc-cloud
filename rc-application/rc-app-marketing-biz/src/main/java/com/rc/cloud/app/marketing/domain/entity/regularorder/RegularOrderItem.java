package com.rc.cloud.app.marketing.domain.entity.regularorder;

import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.RegularOrderItemProduct;

/**
 * @ClassName OrderItem
 * @Author liandy
 * @Date 2023/7/28 17:25
 * @Description 常规订单项
 * @Version 1.0
 */
public class RegularOrderItem {
    /**
     * 订单项标识
     */
    private String id;
    /**
     * 订单标识
     */
    private String orderId;


    /**
     * 商品
     */
    private RegularOrderItemProduct product;




    public RegularOrderItem(String id, String orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    public RegularOrderItemProduct getProduct() {
        return product;
    }

    public RegularOrderItem setProduct(RegularOrderItemProduct product) {
        this.product = product;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

}

