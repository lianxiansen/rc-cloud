package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.entity.common.CreateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author WJF
 * @create 2023-08-03 16:26
 * @description TODO
 */
@Component
public class CartFactory {

    @Autowired
    private CartProductRepository productRepository;

    //创建普通购物车
    public Cart getCart(UserId userId, Integer num, String productId, String productUniqueId, ShopInfo shopInfo) {
        Cart newCart = new Cart();
        newCart.setPayed(0);
        newCart.setType(1);
        newCart.setCreateTime(new CreateTime(LocalDateTime.now()));
        newCart.setUserId(userId);
        newCart.setShopInfo(shopInfo);

        CartProductInfo product = productRepository.getProduct();
        //设置商品属性
        if (product != null) {
            newCart.setCartProductDetail(product.getProductDetail());
            product.getProductSkuDetail().stream().filter(x -> productUniqueId.equals(x.getSkuCode())).findAny().ifPresent(
                    cartProductSkuDetail -> {
                        newCart.setCartProductSkuDetail(cartProductSkuDetail);
                    }
            );
        }

//        cart.setSeckillId(new SeckillId(StringUtils.EMPTY));
//        cart.setCombinationId(new CombinationId(StringUtils.EMPTY));
//        cart.setBargainId(new BargainId(StringUtils.EMPTY));
        newCart.setNewState(0);
        newCart.setNum(num);
        return newCart;
    }
}
