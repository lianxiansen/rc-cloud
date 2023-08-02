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
        po.setShopid(cart.getShopInfo().getShopId().id());
        po.setPayed(cart.getPayed());
        po.setCreatetime(cart.getCreateTime().getTime());
        po.setProductuniqueid(cart.getCartProductDetail().getProductId().id());
        po.setBargainid(cart.getBargainId() == null ? "" : cart.getBargainId().id());
        po.setUserid(cart.getUserId() == null ? "" : cart.getUserId().id());
        po.setId(cart.getId() == null ? "" : cart.getId().id());
        po.setType(cart.getType());
        po.setNum(cart.getNum());
        po.setNewstate(cart.getNewState());
        po.setSeckillid(cart.getSeckillId() == null ? "" : cart.getSeckillId().id());
        po.setCombinationid(cart.getCombinationId() == null ? "" : cart.getCombinationId().id());

        if (cart.getCartProductDetail() != null) {
            //设置购物车产品详细信息
            po.setProductuniqueid(cart.getCartProductDetail().getSkuCode());
            po.setPackingNumber(cart.getCartProductDetail().getPackingNumber());
            po.setSkuAttributes(StringUtils.join(cart.getCartProductDetail().getSkuAttributes(), ","));
            po.setWeight(cart.getCartProductDetail().getWeight());
            po.setCartonSizeHeight(cart.getCartProductDetail().getCartonSizeHeight());
            po.setCartonSizeWidth(cart.getCartProductDetail().getCartonSizeWidth());
            po.setCartonSizeLength(cart.getCartProductDetail().getCartonSizeLength());
            po.setPrice(cart.getCartProductDetail().getPrice());
            po.setProductid(cart.getCartProductDetail().getProductId().id());
            po.setProductName(cart.getCartProductDetail().getProductName());
        }
        return po;
    }

    default Cart convert(CartPO po) {
        Cart cart = new Cart();

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopId(new ShopId(po.getShopid()));
        cart.setShopInfo(shopInfo);

        cart.setPayed(po.getPayed());
        if (po.getCreatetime() != null) {
            cart.setCreateTime(new CreateTime(po.getCreatetime()));
        }
        if (po.getBargainid() != null) {
            cart.setBargainId(new BargainId(po.getBargainid()));
        }
        if (po.getSeckillid() != null) {
            cart.setSeckillId(new SeckillId(po.getSeckillid()));
        }
        if (po.getCombinationid() != null) {
            cart.setCombinationId(new CombinationId(po.getCombinationid()));
        }
        cart.setUserId(new UserId(po.getUserid()));
        cart.setId(new CartId(po.getId()));
        cart.setType(po.getType());
        cart.setNum(po.getNum());
        cart.setNewState(po.getNewstate());

        CartProductDetail productDetail = new CartProductDetail();
        productDetail.setProductId(new ProductId(po.getProductid()));
        productDetail.setProductName(po.getProductName());
        productDetail.setProductImage(po.getProductImage());
        productDetail.setOutId(po.getOutid());
        productDetail.setSkuCode(po.getProductuniqueid());
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
