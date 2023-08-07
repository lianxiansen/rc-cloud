package com.rc.cloud.app.marketing.domain.entity.cart;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-07 13:10
 * @description TODO
 */

public interface CartProductRepository {

    List<CartProductInfo> getProductList();

    CartProductInfo getProduct();
}
