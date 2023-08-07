package com.rc.cloud.app.marketing.domain.entity.common;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * @ClassName ProductItem
 * @Author liandy
 * @Date 2023/7/29 10:59
 * @Description 商品项
 * @Version 1.0
 */

public class ProductItem {
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




    public String getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(String productItemId) {
        this.productItemId = productItemId;
    }

    public String getProductItemName() {
        return productItemName;
    }

    public void setProductItemName(String productItemName) {
        this.productItemName = productItemName;
    }

    public String getProductItemImage() {
        return productItemImage;
    }

    public void setProductItemImage(String productItemImage) {
        this.productItemImage = productItemImage;
    }

    public String getProductItemAttribute() {
        return productItemAttribute;
    }

    public void setProductItemAttribute(String productItemAttribute) {
        this.productItemAttribute = productItemAttribute;
    }

    public BigDecimal getProductItemPrice() {
        return productItemPrice;
    }

    public void setProductItemPrice(BigDecimal productItemPrice) {
        this.productItemPrice = productItemPrice;
    }

    public int getProductItemQuantity() {
        return productItemQuantity;
    }

    public void setProductItemQuantity(int productItemQuantity) {
        this.productItemQuantity = productItemQuantity;
    }

    public static ProductItem mockProductItemA1() {
        ProductItem productItem = new ProductItem();
        productItem.setProductItemId(UUID.randomUUID().toString().substring(0,31));
        productItem.setProductItemPrice(new BigDecimal(100));
        productItem.setProductItemName("单杯调味罐A1");
        productItem.setProductItemImage("http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png");
        productItem.setProductItemAttribute("白色");
        productItem.setProductItemQuantity(2);
        return productItem;
    }
    public static ProductItem mockProductItemA2() {
        ProductItem productItem = new ProductItem();
        productItem.setProductItemId(UUID.randomUUID().toString().substring(0,31));
        productItem.setProductItemPrice(new BigDecimal(100));
        productItem.setProductItemName("单杯调味罐A2");
        productItem.setProductItemImage("http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png");
        productItem.setProductItemAttribute("白色");
        productItem.setProductItemQuantity(2);
        return productItem;
    }
    public static ProductItem mockProductItemB1() {
        ProductItem productItem = new ProductItem();
        productItem.setProductItemId(UUID.randomUUID().toString().substring(0,31));
        productItem.setProductItemPrice(new BigDecimal(100));
        productItem.setProductItemName("单杯调味罐B1");
        productItem.setProductItemImage("http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png");
        productItem.setProductItemAttribute("白色");
        productItem.setProductItemQuantity(2);
        return productItem;
    }
    public static ProductItem mockProductItemB2() {
        ProductItem productItem = new ProductItem();
        productItem.setProductItemId(UUID.randomUUID().toString().substring(0,31));
        productItem.setProductItemPrice(new BigDecimal(100));
        productItem.setProductItemName("单杯调味罐B2");
        productItem.setProductItemImage("http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png");
        productItem.setProductItemAttribute("白色");
        productItem.setProductItemQuantity(2);
        return productItem;
    }
    public BigDecimal getProductItemAmount() {
        return this.productItemPrice.multiply(new BigDecimal(this.productItemQuantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductItem that = (ProductItem) o;
        return getProductItemQuantity() == that.getProductItemQuantity() && getProductItemId().equals(that.getProductItemId()) && getProductItemName().equals(that.getProductItemName()) && getProductItemImage().equals(that.getProductItemImage()) && getProductItemAttribute().equals(that.getProductItemAttribute()) && getProductItemPrice().equals(that.getProductItemPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductItemId(), getProductItemName(), getProductItemImage(), getProductItemAttribute(), getProductItemPrice(), getProductItemQuantity());
    }
}
