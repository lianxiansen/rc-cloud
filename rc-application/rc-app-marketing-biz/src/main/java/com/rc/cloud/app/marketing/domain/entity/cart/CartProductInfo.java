package com.rc.cloud.app.marketing.domain.entity.cart;

import lombok.Data;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-07 13:09
 * @description TODO
 */
@Data
public class CartProductInfo {
    String productId;

    CartProductDetail productDetail;

    List<CartProductSkuDetail> productSkuDetail;
}
