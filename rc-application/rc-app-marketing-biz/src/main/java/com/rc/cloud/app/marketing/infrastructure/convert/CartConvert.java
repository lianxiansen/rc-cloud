package com.rc.cloud.app.marketing.infrastructure.convert;

import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductDetail;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductSkuDetail;
import com.rc.cloud.app.marketing.domain.entity.cart.ShopInfo;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.*;
import com.rc.cloud.app.marketing.domain.entity.common.CreateTime;
import com.rc.cloud.app.marketing.infrastructure.repository.po.CartPO;
import com.rc.cloud.common.core.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-25 9:00
 * @description TODO
 */
@Mapper
public interface CartConvert {
    CartConvert INSTANCE = Mappers.getMapper(CartConvert.class);


    default CartPO convert(Cart cart) {
        CartPO po = new CartPO();
        po.setShopId(cart.getShopInfo().getShopId().id());
        po.setPayed(cart.getPayed());
        po.setCreateTime(cart.getCreateTime().getTime());
        po.setUserId(cart.getUserId() == null ? "" : cart.getUserId().id());
        po.setId(cart.getId() == null ? "" : cart.getId().id());
        po.setType(cart.getType());
        po.setNum(cart.getNum());
        po.setNewState(cart.getNewState());
        if(cart.getBargainId() != null){
            po.setBargainId(cart.getBargainId().id());
        }
        if(cart.getBargainId() != null){
            po.setSeckillId(cart.getSeckillId().id());
        }
        if(cart.getSeckillId() != null){
            po.setCombinationId(cart.getCombinationId().id());
        }

        if (cart.getCartProductDetail() != null) {
            //设置购物车产品详细信息
            po.setProductUniqueid(cart.getCartProductSkuDetail().getSkuCode());
            po.setPackingNumber(cart.getCartProductSkuDetail().getPackingNumber());
            po.setSkuAttributes(StringUtils.join(cart.getCartProductSkuDetail().getSkuAttributes(), ","));
            po.setWeight(cart.getCartProductSkuDetail().getWeight());
            po.setCartonSizeHeight(cart.getCartProductSkuDetail().getCartonSizeHeight());
            po.setCartonSizeWidth(cart.getCartProductSkuDetail().getCartonSizeWidth());
            po.setCartonSizeLength(cart.getCartProductSkuDetail().getCartonSizeLength());
            po.setPrice(cart.getCartProductSkuDetail().getPrice());
            po.setProductId(cart.getCartProductDetail().getId().id());
            po.setProductName(cart.getCartProductDetail().getName());
            po.setProductImage(cart.getCartProductDetail().getMasterImage());
        }
        return po;
    }

    default Cart convert(CartPO po) {
        Cart cart = new Cart();

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopId(new ShopId(po.getShopId()));
        cart.setShopInfo(shopInfo);

        cart.setPayed(po.getPayed());
        if (po.getCreateTime() != null) {
            cart.setCreateTime(new CreateTime(po.getCreateTime()));
        }
        if (po.getBargainId() != null) {
            cart.setBargainId(new BargainId(po.getBargainId()));
        }
        if (po.getSeckillId() != null) {
            cart.setSeckillId(new SeckillId(po.getSeckillId()));
        }
        if (po.getCombinationId() != null) {
            cart.setCombinationId(new CombinationId(po.getCombinationId()));
        }
        cart.setUserId(new UserId(po.getUserId()));
        cart.setId(new CartId(po.getId()));
        cart.setType(po.getType());
        cart.setNum(po.getNum());
        cart.setNewState(po.getNewState());

        CartProductDetail productDetail = new CartProductDetail();
        productDetail.setId(new ProductId(po.getProductId()));
        productDetail.setName(po.getProductName());
        productDetail.setMasterImage(po.getProductImage());
        productDetail.setOutId(po.getOutId());
        productDetail.setSpuCode(po.getSpuCode());
        cart.setCartProductDetail(productDetail);

        CartProductSkuDetail cartProductSkuDetail = new CartProductSkuDetail();
        cartProductSkuDetail.setSkuCode(po.getProductUniqueid());
        cartProductSkuDetail.setPackingNumber(po.getPackingNumber());
        cartProductSkuDetail.setSkuAttributes(Arrays.asList(StringUtils.split(po.getSkuAttributes(), ",")));
        cartProductSkuDetail.setWeight(po.getWeight());
        cartProductSkuDetail.setCartonSizeHeight(po.getCartonSizeHeight());
        cartProductSkuDetail.setCartonSizeWidth(po.getCartonSizeWidth());
        cartProductSkuDetail.setCartonSizeLength(po.getCartonSizeLength());
        cartProductSkuDetail.setPrice(po.getPrice());
        cart.setCartProductSkuDetail(cartProductSkuDetail);
        return cart;
    }

    default List<Cart> convertList(List<CartPO> pos) {
        List<Cart> cartList = new ArrayList<>();
        pos.forEach(po -> {
            Cart convert = convert(po);
            cartList.add(convert);
        });
        return cartList;
    }
}
