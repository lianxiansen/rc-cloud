package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rc.cloud.app.operate.domain.model.cart.Cart;
import com.rc.cloud.app.operate.domain.model.cart.CartRepository;
import com.rc.cloud.app.operate.domain.model.cart.identifier.CartId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.CartConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.CartMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.CartPO;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WJF
 * @create 2023-07-25 8:58
 * @description TODO
 */
@Repository
@DS("order")
public class CartRepositoryImpl implements CartRepository {
    @Resource
    private CartMapper cartMapper;

    @Override
    public CartId create(Cart cart) {
        CartPO cartPO = CartConvert.INSTANCE.convert(cart);
        cartPO.setId(String.valueOf(RandomUtils.nextInt(0,1000000)));
        cartPO.setType(1);
        cartPO.setPayed(0);
        cartPO.setCreatetime(LocalDateTime.now());
        cartMapper.insert(cartPO);
        return new CartId(cartPO.getId());
    }

    @Override
    public void save(Cart cart) {
        validCartExist(cart.getId());
        CartPO cartPO = CartConvert.INSTANCE.convert(cart);
        cartMapper.updateById(cartPO);
    }

    @Override
    public void delete(CartId... cartIds) {
        List<String> idList = Arrays.stream(cartIds).map(cartId -> cartId.id()).collect(Collectors.toList());
        cartMapper.deleteBatchIds(idList);
    }

    @Override
    public Cart findById(CartId cartId) {
        LambdaQueryWrapperX<CartPO> queryWrapperX = new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getId, cartId.id())
                .eq(CartPO::getPayed, 0);
        CartPO cartPO = cartMapper.selectOne(queryWrapperX);
        if (cartPO == null) {
            throw new ServiceException2("购物车已失效");
        }
        return CartConvert.INSTANCE.convert(cartPO);
    }

    @Override
    public List<Cart> getList() {
        LambdaQueryWrapperX<CartPO> queryWrapperX = new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getPayed, 0);
        List<CartPO> cartPOS = cartMapper.selectList(queryWrapperX);
        return CartConvert.INSTANCE.convertList(cartPOS);
    }

    private void validCartExist(CartId cartId) {
        LambdaQueryWrapperX<CartPO> queryWrapperX = new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getId, cartId.id())
                .eq(CartPO::getPayed, 0);
        CartPO cartPO = cartMapper.selectOne(queryWrapperX);
        if (cartPO == null) {
            throw new ServiceException2("购物车已失效");
        }
    }
}
