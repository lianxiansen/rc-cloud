package com.rc.cloud.app.operate.domain.model.cart;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.cart.identifier.*;
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
public class CartDomainService {

    @Resource
    private CartRepository cartRepository;


    public Cart createFromCopy(Cart cart) {
        Cart newCart = new Cart();
        newCart.setPayed(0);
        newCart.setType(1);
        newCart.setCreateTime(new CreateTime(LocalDateTime.now()));
        newCart.setProductUniqueId(cart.getProductUniqueId());
        newCart.setUserId(cart.getUserId());
        newCart.setShopInfo(cart.getShopInfo());
//        cart.setSeckillId(new SeckillId(StringUtils.EMPTY));
//        cart.setCombinationId(new CombinationId(StringUtils.EMPTY));
//        cart.setBargainId(new BargainId(StringUtils.EMPTY));
        newCart.setNewState(0);
        newCart.setNum(cart.getNum());
        return newCart;
    }

    public void delete(CartId cartId) {
        AssertUtils.notNull(cartId, "cartId must be not null");
        //创建购物车业务规则校验
        validateCartExist(cartId);
        cartRepository.delete(cartId);
    }

    public void deleteCartByProductuniqueid(List<ProductUniqueId> productUniqueIds) {
        AssertUtils.notNull(productUniqueIds, "productUniqueId must be not null");
        cartRepository.deleteCartByProductuniqueid(productUniqueIds);
    }

    /**
     * 通过店铺获取购物车列表
     *
     * @return
     */
    public List<Cart> getListByShopIds(List<ShopId> shopIds) {
        return cartRepository.getListByShopIds(shopIds);
    }

    /**
     * 获取购物车列表
     *
     * @return
     */
    public List<Cart> getList(List<ProductUniqueId> productUniqueIdList) {
        return cartRepository.getList(productUniqueIdList);
    }

    /**
     * 操作购物车数量
     *
     * @param cartList
     */
    public void save(List<Cart> cartList) {
        cartList.forEach(cart -> {
            Cart entity = cartRepository.findByProductUniqueId(cart.getProductUniqueId());
            if (entity == null) {
                entity = createFromCopy(cart);
            }
            entity.setNum(cart.getNum());
            cartRepository.save(entity);
        });

    }

    private void validateCartExist(CartId cartId) {
        if (cartRepository.findById(cartId) == null) {
            throw new ServiceException2("购物车已失效");
        }
    }
}
