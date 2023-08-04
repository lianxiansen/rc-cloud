package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.entity.common.CreateTime;

import java.time.LocalDateTime;

/**
 * @author WJF
 * @create 2023-08-03 16:26
 * @description TODO
 */

public class CartFactory {

    //创建普通购物车
    public static Cart getCart(UserId userId, Integer num, ShopInfo shopInfo, CartProductDetail cartProductDetail, CartProductSkuDetail cartProductSkuDetail) {
        Cart newCart = new Cart();
        newCart.setPayed(0);
        newCart.setType(1);
        newCart.setCreateTime(new CreateTime(LocalDateTime.now()));
        newCart.setUserId(userId);
        newCart.setShopInfo(shopInfo);
        newCart.setCartProductDetail(cartProductDetail);
        newCart.setCartProductSkuDetail(cartProductSkuDetail);
//        cart.setSeckillId(new SeckillId(StringUtils.EMPTY));
//        cart.setCombinationId(new CombinationId(StringUtils.EMPTY));
//        cart.setBargainId(new BargainId(StringUtils.EMPTY));
        newCart.setNewState(0);
        newCart.setNum(num);
        return newCart;
    }
}
