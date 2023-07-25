package com.rc.cloud.app.operate.domain.model.cart;

import com.rc.cloud.app.operate.domain.model.cart.identifier.CartId;
import com.rc.cloud.app.operate.domain.model.cart.identifier.ProductUniqueId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-22 10:09
 * @description 购物车领域服务
 */
@Service
public class CartDomainService {

    @Resource
    private CartRepository cartRepository;

    /**
     * 创建购物车
     *
     * @param cart
     * @return
     */
    public CartId create(Cart cart) {
        AssertUtils.notNull(cart, "cart must be not null");
        if (cart.getProductUniqueId() == null) {
            throw new ServiceException2("产品唯一id不能为空");
        }
        return cartRepository.create(cart);
    }

    public void delete(Cart cart) {
        AssertUtils.notNull(cart, "cart must be not null");
        //创建购物车业务规则校验
        validateCartExist(cart.getId());
        cartRepository.delete(cart.getId());
    }

    /**
     * 获取购物车列表
     *
     * @return
     */
    public List<Cart> getList() {
        return cartRepository.getList();
    }


    /**
     * 改变购物车数量
     *
     * @param cart
     */
    public void changeNum(Cart cart) {
        validateCartExist(cart.getId());
        //更新数量
        Cart entity = cartRepository.findById(cart.getId());
        entity.setNum(cart.getNum());
        cartRepository.save(entity);
    }

    private void validateCartExist(CartId cartId) {
        if (cartRepository.findById(cartId) == null) {
            throw new ServiceException2("购物车已失效");
        }
    }
}
