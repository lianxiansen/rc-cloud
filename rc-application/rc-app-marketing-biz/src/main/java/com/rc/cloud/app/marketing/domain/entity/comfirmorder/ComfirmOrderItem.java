package com.rc.cloud.app.marketing.domain.entity.comfirmorder;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.common.Product;

import java.math.BigDecimal;

/**
 * @ClassName PlaceOrder
 * @Author liandy
 * @Date 2023/7/28 13:39
 * @Description 确认订单项
 * @Version 1.0
 */
public class ComfirmOrderItem {
    private String id;
    private String comfirmOrderId;


    private CartId cartId;

    private Product product;


    public ComfirmOrderItem(String id, String comfirmOrderId, CartId cartId, Product product) {
        this.id = id;
        this.comfirmOrderId = comfirmOrderId;
        this.cartId = cartId;
        this.product = product;
    }

    public String getId() {
        return id;
    }



    public CartId getCartId() {
        return cartId;
    }


    Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getProductAmount() {
        BigDecimal[] productAmount = {BigDecimal.ZERO};
        this.product.getProductItems().forEach(item -> {
            productAmount[0] = productAmount[0].add(item.getProductItemAmount());
        });
        return productAmount[0];
    }
}
