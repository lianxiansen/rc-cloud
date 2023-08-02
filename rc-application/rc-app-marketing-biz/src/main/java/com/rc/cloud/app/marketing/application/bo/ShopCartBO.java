package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-26 10:30
 * @description TODO
 */
@Data
public class ShopCartBO {
    ShopBO shopInfo;

    List<CartProductBO> cartProductList;
}
