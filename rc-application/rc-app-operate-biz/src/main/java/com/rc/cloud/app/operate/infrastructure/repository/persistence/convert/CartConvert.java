package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.cart.Cart;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.CartPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-25 9:00
 * @description TODO
 */
@Mapper
public interface CartConvert {
    CartConvert INSTANCE = Mappers.getMapper(CartConvert.class);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "userid", source = "userId.id")
    @Mapping(target = "productuniqueid", source = "productUniqueId.id")
    @Mapping(target = "createtime", source = "createTime.time")
    CartPO convert(Cart cart);

    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "userid", target = "userId.id")
    @Mapping(source = "productuniqueid", target = "productUniqueId.id")
    @Mapping(source = "createtime", target = "createTime.time")
    Cart convert(CartPO po);

    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "userId", target = "userId.id")
    @Mapping(source = "productuniqueid", target = "productUniqueId.id")
    @Mapping(source = "createtime", target = "createTime.time")
    List<Cart> convertList(List<CartPO> pos);
}
