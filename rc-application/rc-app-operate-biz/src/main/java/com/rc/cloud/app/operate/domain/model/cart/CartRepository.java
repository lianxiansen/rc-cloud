package com.rc.cloud.app.operate.domain.model.cart;

import com.rc.cloud.app.operate.domain.model.cart.identifier.CartId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.ProductUniqueId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.ShopId;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-22 10:09
 * @description 购物车资源库
 */

public interface CartRepository {

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
     * 删除购物车
     *
     * @param cartIds CartId集合
     */
    void deleteCartByProductuniqueid(List<ProductUniqueId> productUniqueId);


    /**
     * 获取单个购物车商品信息
     *
     * @param cartId
     * @return Cart
     */
    Cart findByProductUniqueId(ProductUniqueId productUniqueId);

    /**
     * 通过店铺获取购物车列表
     *
     * @return List<Cart>
     */
    List<Cart> getListByShopIds(List<ShopId> shopIds);

    /**
     * 获取购物车列表
     *
     * @return List<Cart>
     */
    List<Cart> getList(List<ProductUniqueId> productUniqueIdList);
}
