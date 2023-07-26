package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.CartBO;
import com.rc.cloud.app.operate.application.dto.CartDTO;
import com.rc.cloud.app.operate.domain.model.cart.Cart;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.CartPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
    @Mapping(target = "productuniqueid", source = "productUniqueId.id")
    @Mapping(target = "createtime", source = "createTime.time")
    CartBO convert(Cart cart);

    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "userId", target = "userId.id")
    @Mapping(source = "productuniqueid", target = "productUniqueId.id")
    @Mapping(source = "createtime", target = "createTime.time")
    Cart convert(CartBO po);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "userId", source = "userId.id")
    @Mapping(target = "productuniqueid", source = "productUniqueId.id")
    @Mapping(target = "createtime", source = "createTime.time")
    List<CartBO> convertList(List<Cart> pos);

    @Mapping(source = "productuniqueid", target = "productUniqueId.id")
    @Mapping(source = "shopid", target = "shopInfo.shopId.id")
    Cart convert(CartDTO dto);

    @Mapping(source = "productuniqueid", target = "productUniqueId.id")
    List<Cart> convert(List<CartDTO> dto);
}
