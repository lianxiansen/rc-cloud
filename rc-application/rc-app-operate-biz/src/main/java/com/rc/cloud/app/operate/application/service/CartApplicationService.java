package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.CartListBO;
import com.rc.cloud.app.operate.application.bo.ShopCartBO;
import com.rc.cloud.app.operate.application.dto.CartDTO;
import com.rc.cloud.app.operate.domain.model.price.PriceContext;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-01 9:23
 * @description TODO
 */

public interface CartApplicationService {

    List<ShopCartBO> getCartListByShopIds(List<String> shopIds);

    PriceContext calPrice(List<String> productUniqueIdList);

    CartListBO getCartList(List<String> productUniqueIdList);

    Boolean saveCart(List<CartDTO> cartDTOList);

    void deleteCartByProductuniqueid(List<String> productuniqueids);
}
