package com.rc.cloud.app.marketing.application.bo.convert;

import com.rc.cloud.app.marketing.application.bo.CartBO;
import com.rc.cloud.app.marketing.application.bo.CartListBO;
import com.rc.cloud.app.marketing.domain.entity.price.ProductPack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-26 14:42
 * @description TODO
 */

public class PriceConvert {
    public static List<ProductPack> convertProductPack(CartListBO bo) {
        List<ProductPack> productPackList = new ArrayList<>();
        List<CartBO> cartList = bo.getCartList();
        cartList.forEach(x -> {
            ProductPack pack = new ProductPack();
            pack.setSalePrice(x.getCartProductDetailBO().getPrice());
            pack.setCount(x.getNum());
            pack.setSkuId(Long.valueOf(x.getProductuniqueid()));

            productPackList.add(pack);
        });
        return productPackList;
    }

}
