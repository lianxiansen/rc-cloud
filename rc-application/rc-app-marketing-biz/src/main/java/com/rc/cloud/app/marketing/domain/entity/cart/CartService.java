package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductUniqueId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ShopId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.infrastructure.util.ListUtil;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author WJF
 * @create 2023-07-22 10:09
 * @description 购物车领域服务
 */
@Service
public class CartService {

    @Resource
    private CartRepository cartRepository;

    @Resource
    private CartProductRepository productRepository;

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

        List<Cart> cartList = cartRepository.getListByShopIds(userId, shopIds);
        //获取最新商品信息，用于判断购物车是否过期
        List<CartProductInfo> productList = productRepository.getProductList();

        setCartExpireByProduct(cartList, productList);
        return cartList;
    }

    //设置商品购物车商品过期
    private void setCartExpireByProduct(List<Cart> cartList, List<CartProductInfo> productList) {
        cartList.forEach(cart -> {
            cart.setState(ExpireState.PRODUCT_EXPIRE.getCode());
            //如果有商品信息并且商品规格正确
            Optional<CartProductInfo> anyCartProductInfo = productList.stream().filter(product -> cart.getCartProductDetail().getId().id().equals(product.getProductId())).findAny();
            if (anyCartProductInfo.isPresent()) {
                CartProductInfo cartProductInfo = anyCartProductInfo.get();
                Optional<CartProductSkuDetail> anyCartProductSkuDetail = cartProductInfo.getProductSkuDetail().stream().filter(cartProductSkuDetail -> cart.getCartProductSkuDetail().getSkuCode().equals(cartProductSkuDetail.getSkuCode())).findAny();
                if (anyCartProductSkuDetail.isPresent()
                        && ListUtil.isEqual(cart.getCartProductSkuDetail().getSkuAttributes(), anyCartProductSkuDetail.get().getSkuAttributes())) {
                    cart.setState(ExpireState.NOT_EXPIRE.getCode());
                }
            }
        });

    }

    /**
     * 获取购物车列表
     *
     * @return
     */
    public List<Cart> getList(UserId userId, List<ProductUniqueId> productUniqueIdList) {
        List<Cart> cartList = cartRepository.getList(userId, productUniqueIdList);
        //获取最新商品信息，用于判断购物车是否过期
        List<CartProductInfo> productList = productRepository.getProductList();
        setCartExpireByProduct(cartList, productList);
        return cartList;
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

    public List<Cart> findCarts(Customer customer,List<String> cartIds) {
        return null;
    }
}
