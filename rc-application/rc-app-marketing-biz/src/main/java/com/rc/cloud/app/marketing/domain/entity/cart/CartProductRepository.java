package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.api.product.dto.ProductSkuRequest;

import java.util.List;
import java.util.Map;

/**
 * @author WJF
 * @create 2023-08-07 13:10
 * @description TODO
 */

public interface CartProductRepository {

    List<CartProductSkuInfo> getProductList(List<Cart> dto);

    Map<String, Object> getProduct(String productId, String productSkuId);
}
