package com.rc.cloud.app.marketing.domain.entity.comfirmorder;

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


    private String cartItemId;

    private String productId;
    private String productName;
    private String productImage;
    private String productArticleNo;

    private ProductItem productItem;



    public ComfirmOrderItem(String id, String comfirmOrderId, String cartItemId, String productId, String productName, String productImage, String productArticleNo,ProductItem productItem ) {
        this.id = id;
        this.comfirmOrderId = comfirmOrderId;
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productArticleNo = productArticleNo;
        this.productItem = productItem;
    }

    public String getId() {
        return id;
    }

    public String getComfirmOrderId() {
        return comfirmOrderId;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductArticleNo() {
        return productArticleNo;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }
}
