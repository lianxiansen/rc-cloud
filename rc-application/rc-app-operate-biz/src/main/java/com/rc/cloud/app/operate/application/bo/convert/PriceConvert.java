package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.CartBO;
import com.rc.cloud.app.operate.application.bo.CartListBO;
import com.rc.cloud.app.operate.domain.model.price.PriceCalParam;
import com.rc.cloud.app.operate.domain.model.price.ProductPack;
import jdk.nashorn.internal.ir.ReturnNode;

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
