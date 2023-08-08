package com.rc.cloud.app.marketing.application.bo.convert;

import com.rc.cloud.api.product.bo.ProductBO;
import com.rc.cloud.api.product.bo.ProductSkuBO;
import com.rc.cloud.app.marketing.application.bo.CartBO;
import com.rc.cloud.app.marketing.application.bo.CartProductDetailBO;
import com.rc.cloud.app.marketing.application.bo.CartProductSkuDetailBO;
import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductDetail;
import com.rc.cloud.app.marketing.application.dto.CartDTO;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductSkuDetail;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WJF
 * @create 2023-07-25 11:23
 * @description TODO
 */
@Mapper
public interface CartConvert {
    CartConvert INSTANCE = Mappers.getMapper(CartConvert.class);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "userId", source = "userId.id")
    @Mapping(target = "createTime", source = "createTime.time")
    @Mapping(target = "shopId", source = "shopInfo.shopId.id")
    @Mapping(target = "combinationId", source = "combinationId.id")
    @Mapping(target = "seckillId", source = "seckillId.id")
    @Mapping(target = "bargainId", source = "bargainId.id")
    CartBO convertBase(Cart cart);

    @Mapping(target = "id", source = "id.id")
    CartProductDetailBO convertDetail(CartProductDetail detail);

    CartProductSkuDetailBO convertSkuDetail(CartProductSkuDetail detail);

    default CartProductDetail convert(ProductBO productBO) {
        CartProductDetail detail = new CartProductDetail();

        detail.setName(productBO.getName());
        detail.setId(new ProductId(productBO.getId()));
        detail.setMasterImage(productBO.getMasterImages().get(0).getUrl());
        return detail;
    }

    default CartProductSkuDetail convert(ProductSkuBO productSkuBO) {
        CartProductSkuDetail detail = new CartProductSkuDetail();
        //设置产品sku属性
        detail.setOutId(productSkuBO.getOutId());
        detail.setSkuCode(productSkuBO.getSkuCode());
        detail.setCartonSizeWidth(productSkuBO.getCartonSizeWidth());
        detail.setCartonSizeLength(productSkuBO.getCartonSizeLength());
        detail.setCartonSizeHeight(productSkuBO.getCartonSizeHeight());
        detail.setPackingNumber(productSkuBO.getPackingNumber());
        detail.setWeight(productSkuBO.getWeight());
        detail.setPrice(productSkuBO.getPrice());
        detail.setSkuAttributes(productSkuBO.getSkuAttributes().stream().map(attr -> attr.getAttributeValue()).collect(Collectors.toList()));
        return detail;
    }

    default List<CartBO> convertList(List<Cart> carts) {
        List<CartBO> cartBOS = new ArrayList<>();
        carts.forEach(cart -> {
            CartBO bo = convertBase(cart);
            bo.setCartProductDetailBO(convertDetail(cart.getCartProductDetail()));
            bo.setCartProductSkuDetailBO(convertSkuDetail(cart.getCartProductSkuDetail()));
            cartBOS.add(bo);
        });
        return cartBOS;
    }

    @Mapping(source = "productUniqueid", target = "cartProductSkuDetail.skuCode")
    @Mapping(source = "shopId", target = "shopInfo.shopId.id")
    @Mapping(source = "productId", target = "cartProductDetail.id.id")
    Cart convert(CartDTO dto);

    List<Cart> convert(List<CartDTO> dto);
}
