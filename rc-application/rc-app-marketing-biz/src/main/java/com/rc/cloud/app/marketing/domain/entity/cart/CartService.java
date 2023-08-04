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
    public void create(List<Cart> cartList) {
        cartList.forEach(cart -> {
            Cart findOne = cartRepository.findByProductUniqueId(cart.getUserId(), new ProductUniqueId(cart.getCartProductSkuDetail().getSkuCode()));
            if (findOne != null) {
                cart.setId(findOne.getId());
            }
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
