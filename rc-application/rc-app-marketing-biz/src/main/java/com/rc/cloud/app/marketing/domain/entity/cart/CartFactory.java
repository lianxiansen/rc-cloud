package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.entity.common.CreateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

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
    public Cart newCart(UserId userId, Integer num, String productId, String productUniqueId, ShopInfo shopInfo) {
        Cart newCart = new Cart();
        newCart.setPayed(0);
        newCart.setType(1);
        newCart.setCreateTime(new CreateTime(LocalDateTime.now()));
        newCart.setUserId(userId);
        newCart.setShopInfo(shopInfo);

        Map<String, Object> product = productRepository.getProduct(productId, productUniqueId);
        if (product != null && product.size() > 0) {
            CartProductDetail detail = (CartProductDetail) product.get(CartAttributeKey.PRODUCT_DETAIL);
            CartProductSkuDetail skuDetail = (CartProductSkuDetail) product.get(CartAttributeKey.PRODUCT_SKU_DETAIL);

            //设置商品属性
            newCart.setCartProductDetail(detail);
            //设置商品sku属性
            newCart.setCartProductSkuDetail(skuDetail);
        }
//        cart.setSeckillId(new SeckillId(StringUtils.EMPTY));
//        cart.setCombinationId(new CombinationId(StringUtils.EMPTY));
//        cart.setBargainId(new BargainId(StringUtils.EMPTY));
        newCart.setNewState(0);
        newCart.setNum(num);
        return newCart;
    }
}
