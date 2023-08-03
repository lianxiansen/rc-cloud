package com.rc.cloud.app.marketing.application.service;

import com.rc.cloud.app.marketing.application.bo.CartBO;
import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.rc.cloud.app.marketing.application.dto.CartDTO;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-01 9:23
 * @description TODO
 */

public interface CartApplicationService {

    List<CartBO> getCartListByShopIds(List<String> shopIds);

    PriceContext calPrice(List<String> productUniqueIdList);

    List<CartBO> getCartList(List<String> productUniqueIdList);

    Boolean saveCart(List<CartDTO> cartDTOList);

    void deleteCartByProductUniqueid(List<String> productuniqueids);
}
