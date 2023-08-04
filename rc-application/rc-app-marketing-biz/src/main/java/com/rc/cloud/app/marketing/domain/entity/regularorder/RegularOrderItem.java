package com.rc.cloud.app.marketing.domain.entity.regularorder;

import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;

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
    private Product product;

    /**
     * 商品sku
     */
    private ProductItem productItem;




    public RegularOrderItem(String id, String orderId) {
        this.id = id;
        this.orderId = orderId;
    }




    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }
}

