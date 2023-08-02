package com.rc.cloud.app.marketing.application.bo.convert;

import com.rc.cloud.api.product.bo.ProductBO;
import com.rc.cloud.api.product.bo.ProductSkuBO;
import com.rc.cloud.app.marketing.application.bo.CartBO;
import com.rc.cloud.app.marketing.application.bo.CartProductDetailBO;
import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.cart.CartProductDetail;
import com.rc.cloud.app.marketing.application.dto.CartDTO;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Mapping(target = "productId", source = "cartProductDetail.productId.id")
    @Mapping(target = "productUniqueid", source = "cartProductDetail.skuCode")
    @Mapping(target = "combinationId", source = "combinationId.id")
    @Mapping(target = "seckillId", source = "seckillId.id")
    @Mapping(target = "bargainId", source = "bargainId.id")
    CartBO convertBase(Cart cart);

    @Mapping(target = "productId", source = "productId.id")
    CartProductDetailBO convertDetail(CartProductDetail detail);

    default CartProductDetail convert(ProductBO productBO, String skuCode) {
        CartProductDetail detail = new CartProductDetail();

        List<ProductSkuBO> skus = productBO.getSkus();
        //产品规格不存在，跳过
        Optional<ProductSkuBO> skuBO = skus.stream().filter(sku -> skuCode.equals(sku.getSkuCode())).findAny();
        if (!skuBO.isPresent()) {
            return detail;
        }
        ProductSkuBO productSkuBO = skuBO.get();
        //设置产品属性
        detail.setProductName(productBO.getName());
        detail.setProductId(new ProductId(productBO.getId()));
        detail.setSkuCode(skuCode);
        //设置产品sku属性
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
            cartBOS.add(bo);
        });
        return cartBOS;
    }

    @Mapping(source = "productUniqueid", target = "cartProductDetail.skuCode")
    @Mapping(source = "shopId", target = "shopInfo.shopId.id")
    @Mapping(source = "productId", target = "cartProductDetail.productId.id")
    Cart convert(CartDTO dto);

    @Mapping(source = "productUniqueid", target = "cartProductDetail.skuCode")
    @Mapping(source = "shopId", target = "shopInfo.shopId.id")
    @Mapping(source = "productId", target = "cartProductDetail.productId.id")
    List<Cart> convert(List<CartDTO> dto);
}
