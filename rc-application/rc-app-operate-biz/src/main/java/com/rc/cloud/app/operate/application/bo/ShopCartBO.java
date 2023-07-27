package com.rc.cloud.app.operate.application.bo;

import com.rc.cloud.app.operate.domain.model.cart.ShopInfo;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-26 10:30
 * @description TODO
 */
@Data
public class ShopCartBO {
    ShopBO shopInfo;

    CartListBO cartList;
}
