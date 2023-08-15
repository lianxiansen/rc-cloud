package com.rc.cloud.app.marketing.domain.entity.order.valobj;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @ClassName Product
 * @Author liandy
 * @Date 2023/8/2 14:18
 * @Description 商品
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class Product {
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
     * 商品项属性名称
     */
    private String productAttribute;
    /**
     * 商品项单价
     */
    private BigDecimal productPrice;




    public Product(String productId, String productName, String productImage, String productArticleNo, String productAttribute, BigDecimal productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productArticleNo = productArticleNo;
        this.productAttribute = productAttribute;
        this.productPrice = productPrice;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return getProductId().equals(product.getProductId()) && getProductAttribute().equals(product.getProductAttribute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductAttribute());
    }

    public static Product mockProductA1() {
        String productId = "7747a149-5a79-4e37-8e42-061e431";
        String productName = "单杯调味罐A";
        String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        String productArticleNo = "56226";
        String productAttribute="黑色";
        BigDecimal productPrice=new BigDecimal(100);
        Product product = new Product(productId, productName, productImage, productArticleNo,productAttribute,productPrice);
        return product;
    }
    public static Product mockProductA2() {
        String productId = "7747a149-5a79-4e37-8e42-061e431";
        String productName = "单杯调味罐A";
        String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        String productArticleNo = "56226";
        String productAttribute="白色";
        BigDecimal productPrice=new BigDecimal(100);
        Product product = new Product(productId, productName, productImage, productArticleNo,productAttribute,productPrice);
        return product;
    }
    public static Product mockProductB1() {
        String productId = "7747a149-5a79-4e37-8e42-061e432";
        String productName = "单杯调味罐B";
        String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        String productArticleNo = "56227";
        String productAttribute="黑色";
        BigDecimal productPrice=new BigDecimal(100);
        Product product = new Product(productId, productName, productImage, productArticleNo,productAttribute,productPrice);
        return product;
    }
    public static Product mockProductB2() {
        String productId = "7747a149-5a79-4e37-8e42-061e432";
        String productName = "单杯调味罐B";
        String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        String productArticleNo = "56227";
        String productAttribute="白色";
        BigDecimal productPrice=new BigDecimal(100);
        Product product = new Product(productId, productName, productImage, productArticleNo,productAttribute,productPrice);
        return product;
    }

}
