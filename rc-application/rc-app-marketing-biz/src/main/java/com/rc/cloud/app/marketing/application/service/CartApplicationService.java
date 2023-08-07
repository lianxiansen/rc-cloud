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

    List<CartBO> getCartListByShopIds(String user, List<String> shopIds);

    Boolean saveCart(String user, List<CartDTO> cartDTOList);

    void deleteCartByProductUniqueid(String user,List<String> productuniqueids);

    PriceContext calPrice(String user, List<String> productUniqueIdList);

    List<CartBO> getCartList(String user, List<String> productUniqueIdList);

}
