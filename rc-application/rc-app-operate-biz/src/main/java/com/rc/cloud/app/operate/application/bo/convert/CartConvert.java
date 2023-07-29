package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.CartBO;
import com.rc.cloud.app.operate.application.bo.CartProductDetailBO;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.application.dto.CartDTO;
import com.rc.cloud.app.operate.domain.model.cart.Cart;
import com.rc.cloud.app.operate.domain.model.cart.CartProductDetail;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.CartPO;
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
    @Mapping(target = "createtime", source = "createTime.time")
    @Mapping(target = "shopid", source = "shopInfo.shopId.id")
    @Mapping(target = "productid", source = "cartProductDetail.productId.id")
    @Mapping(target = "productuniqueid", source = "cartProductDetail.skuCode")
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

    @Mapping(source = "productuniqueid", target = "cartProductDetail.skuCode")
    @Mapping(source = "shopid", target = "shopInfo.shopId.id")
    @Mapping(source = "productid", target = "cartProductDetail.productId.id")
    Cart convert(CartDTO dto);

    @Mapping(source = "productuniqueid", target = "cartProductDetail.skuCode")
    @Mapping(source = "shopid", target = "shopInfo.shopId.id")
    @Mapping(source = "productid", target = "cartProductDetail.productId.id")
    List<Cart> convert(List<CartDTO> dto);
}
