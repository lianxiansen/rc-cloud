package com.rc.cloud.app.marketing.application.service;

import com.rc.cloud.app.marketing.application.bo.CartListBO;
import com.rc.cloud.app.marketing.application.bo.ShopCartBO;
import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.rc.cloud.app.marketing.application.dto.CartDTO;

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
