package com.rc.cloud.app.marketing.domain.entity.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName Product
 * @Author liandy
 * @Date 2023/8/2 14:18
 * @Description TODO
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class Product {
    private String productId;
    private String productName;
    private String productImage;
    private String productArticleNo;

    public Product(String productId, String productName, String productImage, String productArticleNo) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productArticleNo = productArticleNo;
    }
}
