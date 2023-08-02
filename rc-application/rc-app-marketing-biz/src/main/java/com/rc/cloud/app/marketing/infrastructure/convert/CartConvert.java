package com.rc.cloud.app.marketing.infrastructure.convert;

import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductDetail;
import com.rc.cloud.app.marketing.domain.entity.cart.ShopInfo;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.*;
import com.rc.cloud.app.marketing.domain.valobj.CreateTime;
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
        po.setProductUniqueid(cart.getCartProductDetail().getProductId().id());
        po.setBargainId(cart.getBargainId() == null ? "" : cart.getBargainId().id());
        po.setUserId(cart.getUserId() == null ? "" : cart.getUserId().id());
        po.setId(cart.getId() == null ? "" : cart.getId().id());
        po.setType(cart.getType());
        po.setNum(cart.getNum());
        po.setNewState(cart.getNewState());
        po.setSeckillId(cart.getSeckillId() == null ? "" : cart.getSeckillId().id());
        po.setCombinationId(cart.getCombinationId() == null ? "" : cart.getCombinationId().id());

        if (cart.getCartProductDetail() != null) {
            //设置购物车产品详细信息
            po.setProductUniqueid(cart.getCartProductDetail().getSkuCode());
            po.setPackingNumber(cart.getCartProductDetail().getPackingNumber());
            po.setSkuAttributes(StringUtils.join(cart.getCartProductDetail().getSkuAttributes(), ","));
            po.setWeight(cart.getCartProductDetail().getWeight());
            po.setCartonSizeHeight(cart.getCartProductDetail().getCartonSizeHeight());
            po.setCartonSizeWidth(cart.getCartProductDetail().getCartonSizeWidth());
            po.setCartonSizeLength(cart.getCartProductDetail().getCartonSizeLength());
            po.setPrice(cart.getCartProductDetail().getPrice());
            po.setProductId(cart.getCartProductDetail().getProductId().id());
            po.setProductName(cart.getCartProductDetail().getProductName());
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
        productDetail.setProductId(new ProductId(po.getProductId()));
        productDetail.setProductName(po.getProductName());
        productDetail.setProductImage(po.getProductImage());
        productDetail.setOutId(po.getOutId());
        productDetail.setSkuCode(po.getProductUniqueid());
        productDetail.setPackingNumber(po.getPackingNumber());
        productDetail.setSkuAttributes(Arrays.asList(StringUtils.split(po.getSkuAttributes(), ",")));
        productDetail.setWeight(po.getWeight());
        productDetail.setCartonSizeHeight(po.getCartonSizeHeight());
        productDetail.setCartonSizeWidth(po.getCartonSizeWidth());
        productDetail.setCartonSizeLength(po.getCartonSizeLength());
        productDetail.setPrice(po.getPrice());
        cart.setCartProductDetail(productDetail);
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
