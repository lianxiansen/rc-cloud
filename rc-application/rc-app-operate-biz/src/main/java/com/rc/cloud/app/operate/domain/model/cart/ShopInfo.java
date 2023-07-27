package com.rc.cloud.app.operate.domain.model.cart;

import com.rc.cloud.app.operate.domain.model.cart.identifier.ShopId;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-25 16:03
 * @description TODO
 */
@Data
public class ShopInfo {
    /**
     * 店铺id
     */
    private ShopId shopId;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺图片
     */
    private String imageUrl;
}
