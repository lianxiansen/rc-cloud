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


    public void delete(CartId cartId) {
        AssertUtils.notNull(cartId, "cartId must be not null");
        //创建购物车业务规则校验
        validateCartExist(cartId);
        cartRepository.delete(cartId);
    }

    public void deleteCartByProductuniqueid(List<ProductUniqueId> productUniqueIds) {
        AssertUtils.notNull(productUniqueIds, "productUniqueId must be not null");
        cartRepository.deleteCartByProductuniqueid(productUniqueIds);
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
     * 获取购物车列表
     *
     * @return
     */
    public List<Cart> getList(List<ProductUniqueId> productUniqueIdList) {
        return cartRepository.getList(productUniqueIdList);
    }

    /**
     * 操作购物车数量
     *
     * @param cartList
     */
    public void save(List<Cart> cartList) {
        cartList.forEach(cart -> {
            Cart entity = cartRepository.findByProductUniqueId(cart.getProductUniqueId());
            if (entity == null) {
                entity = cart;
            }
            entity.setNum(cart.getNum());
            cartRepository.save(entity);
        });

    }

    private void validateCartExist(CartId cartId) {
        if (cartRepository.findById(cartId) == null) {
            throw new ServiceException2("购物车已失效");
        }
    }
}
