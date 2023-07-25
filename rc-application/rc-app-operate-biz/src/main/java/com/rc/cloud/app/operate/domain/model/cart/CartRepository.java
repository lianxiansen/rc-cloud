package com.rc.cloud.app.operate.domain.model.cart;

import com.rc.cloud.app.operate.domain.model.cart.identifier.CartId;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-22 10:09
 * @description 购物车资源库
 */

public interface CartRepository {

    /**
     * 创建购物车
     *
     * @param cart
     */
    CartId create(Cart cart);

    /**
     * 更新购物车
     *
     * @param cart
     */
    void save(Cart cart);

    /**
     * 删除购物车
     *
     * @param cartIds CartId集合
     */
    void delete(CartId... cartIds);

    /**
     * 获取单个购物车信息
     *
     * @param cartId
     * @return Cart
     */
    Cart findById(CartId cartId);

    /**
     * 获取购物车列表
     *
     * @return List<Cart>
     */
    List<Cart> getList();
}
