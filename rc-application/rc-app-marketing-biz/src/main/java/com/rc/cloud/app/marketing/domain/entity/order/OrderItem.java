package com.rc.cloud.app.marketing.domain.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
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

    /**
     * 商品
     */
    private Product product;

    /**
     * 商品sku
     */
    private ProductItem productItem;



    public OrderItem(String id, String orderId) {
        this.id = id;
        this.orderId = orderId;
    }




    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public ProductItem getProductItem() {
        return productItem;
    }


}

