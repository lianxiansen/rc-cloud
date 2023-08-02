package com.rc.cloud.app.marketing.domain.entity.cart.specification;

import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @author WJF
 * @create 2023-07-24 9:51
 * @description TODO
 */

public class CommonCartSpecification extends AbstractSpecification<Cart> {

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        AssertUtils.notNull(cart, "cart must be not null");
        if (cart.getNum() <= 0) {
            throw new ServiceException2("数量必须大于0");
        }
        return true;
    }
}
