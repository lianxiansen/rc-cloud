package com.rc.cloud.app.marketing.domain.entity.regularorder.valobj;

import com.rc.cloud.app.marketing.domain.entity.common.Product;

import java.math.BigDecimal;

/**
 * @ClassName RegularOrderProduct
 * @Author liandy
 * @Date 2023/8/8 14:32
 * @Description 常规订单项商品
 * @Version 1.0
 */
public class RegularOrderItemProduct {
    /**
     * 商品唯一标识
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImage;
    /**
     * 商品货号
     */
    private String productArticleNo;




    /**
     * 商品项id
     */
    private String productItemId;

    /**
     * 商品项名称
     */
    private String productItemName;
    /**
     * 商品项图片地址
     */
    private String productItemImage;
    /**
     * 商品项属性名称
     */
    private String productItemAttribute;
    /**
     * 商品项单价
     */
    private BigDecimal productItemPrice;
    /**
     * 商品项数量
     */
    private int productItemQuantity;

    public RegularOrderItemProduct(Product product, Product.ProductItem item) {
        this.productId=product.getProductId();
        this.productName=product.getProductName();
        this.productImage=product.getProductImage();
        this.productArticleNo=product.getProductArticleNo();

        this.productItemId=item.getProductItemId();
        this.productItemName=item.getProductItemName();
        this.productItemImage=item.getProductItemImage();
        this.productItemAttribute=item.getProductItemAttribute();
        this.productItemPrice=item.getProductItemPrice();
        this.productItemQuantity=item.getProductItemQuantity();
    }

    public BigDecimal getProductAmount() {
        return new BigDecimal(productItemQuantity).multiply(productItemPrice);
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

    public String getProductItemId() {
        return productItemId;
    }

    public String getProductItemName() {
        return productItemName;
    }

    public String getProductItemImage() {
        return productItemImage;
    }

    public String getProductItemAttribute() {
        return productItemAttribute;
    }

    public BigDecimal getProductItemPrice() {
        return productItemPrice;
    }

    public int getProductItemQuantity() {
        return productItemQuantity;
    }

    public BigDecimal getProductItemAmount() {
        return this.productItemPrice.multiply(new BigDecimal(this.productItemQuantity));
    }
}
