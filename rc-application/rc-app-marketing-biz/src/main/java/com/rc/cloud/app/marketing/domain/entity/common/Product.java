package com.rc.cloud.app.marketing.domain.entity.common;

import lombok.Data;
import lombok.experimental.Accessors;

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

    public Product(String productId, String productName, String productImage, String productArticleNo) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productArticleNo = productArticleNo;
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
        return getProductId().equals(product.getProductId()) && getProductName().equals(product.getProductName()) && getProductImage().equals(product.getProductImage()) && getProductArticleNo().equals(product.getProductArticleNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductImage(), getProductArticleNo());
    }

    public static Product mockProductA() {
        String productId = "7747a149-5a79-4e37-8e42-061e434";
        String productName = "单杯调味罐A";
        String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        String productArticleNo = "56226";
        Product product = new Product(productId, productName, productImage, productArticleNo);
        return product;
    }
    public static Product mockProductB() {
        String productId = "7747a149-5a79-4e37-8e42-061e434";
        String productName = "单杯调味罐B";
        String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        String productArticleNo = "56226";
        Product product = new Product(productId, productName, productImage, productArticleNo);
        return product;
    }
}
