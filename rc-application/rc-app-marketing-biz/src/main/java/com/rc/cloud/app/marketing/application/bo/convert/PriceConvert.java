package com.rc.cloud.app.marketing.application.bo.convert;

import com.rc.cloud.app.marketing.application.bo.CartBO;
import com.rc.cloud.app.marketing.domain.entity.price.ProductPack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-26 14:42
 * @description TODO
 */

public class PriceConvert {
    public static List<ProductPack> convertProductPack(List<CartBO> cartList) {
        List<ProductPack> productPackList = new ArrayList<>();
        cartList.forEach(x -> {
            ProductPack pack = new ProductPack();
            pack.setSalePrice(x.getCartProductSkuDetailBO().getPrice());
            pack.setCount(x.getNum());
            pack.setSkuId(Long.valueOf(x.getCartProductSkuDetailBO().getSkuCode()));

            productPackList.add(pack);
        });
        return productPackList;
    }

}
