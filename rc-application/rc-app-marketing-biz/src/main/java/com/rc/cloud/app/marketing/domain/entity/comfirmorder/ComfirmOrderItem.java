package com.rc.cloud.app.marketing.domain.entity.comfirmorder;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;

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

    private ProductItem productItem;



    public ComfirmOrderItem(String id, String comfirmOrderId, CartId cartId, Product product, ProductItem productItem ) {
        this.id = id;
        this.comfirmOrderId = comfirmOrderId;
        this.cartId = cartId;
        this.product = product;
        this.productItem = productItem;
    }

    public String getId() {
        return id;
    }

    public String getComfirmOrderId() {
        return comfirmOrderId;
    }

    public CartId getCartId() {
        return cartId;
    }




    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
