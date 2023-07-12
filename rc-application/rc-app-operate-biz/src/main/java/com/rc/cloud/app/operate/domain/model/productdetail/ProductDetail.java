package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;


public class ProductDetail
{
    private ProductId productId;

    private String detail;

    public ProductDetail(ProductId productId, String detail) {
        this.productId = productId;
        this.detail = detail;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getDetail() {
        return detail;
    }
}
