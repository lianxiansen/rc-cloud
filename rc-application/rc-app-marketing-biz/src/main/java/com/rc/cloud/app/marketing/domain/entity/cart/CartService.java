package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductUniqueId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ShopId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.valobj.CreateTime;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-22 10:09
 * @description 购物车领域服务
 */
@Service
public class CartService {

    @Resource
    private CartRepository cartRepository;

    //创建普通购物车
    public Cart createCommonCart(UserId userId, Integer num, ShopInfo shopInfo, CartProductDetail cartProductDetail, CartProductSkuDetail cartProductSkuDetail) {
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

    public void delete(CartId cartId) {
        AssertUtils.notNull(cartId, "cartId must be not null");
        //删除购物车业务规则校验
        validateCartExist(cartId);
        cartRepository.delete(cartId);
    }

    public void deleteCartByProductuniqueid(UserId userId, List<ProductUniqueId> productUniqueIds) {
        AssertUtils.notNull(productUniqueIds, "productUniqueId must be not null");
        cartRepository.deleteCartByProductuniqueid(userId, productUniqueIds);
    }

    /**
     * 通过店铺获取购物车列表
     *
     * @return
     */
    public List<Cart> getListByShopIds(UserId userId, List<ShopId> shopIds) {
        return cartRepository.getListByShopIds(userId, shopIds);
    }

    /**
     * 获取购物车列表
     *
     * @return
     */
    public List<Cart> getList(UserId userId, List<ProductUniqueId> productUniqueIdList) {
        return cartRepository.getList(userId, productUniqueIdList);
    }


    /**
     * 操作购物车数量
     *
     * @param cartList
     * @description
     */
    public void save(List<Cart> cartList) {
        cartList.forEach(cart -> {
            cartRepository.save(cart);
        });
    }


    private void validateCartExist(CartId cartId) {
        if (cartRepository.findById(cartId) == null) {
            throw new ServiceException2("购物车已失效");
        }
    }

    public List<Cart> findCarts(List<String> cartIds) {
        return null;
    }
}
