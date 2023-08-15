package com.rc.cloud.app.marketing.domain.entity.order.comfirmorder;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.OrderLine;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.ProductQuality;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName ComfirmOrderLine
 * @Author liandy
 * @Date 2023/7/28 13:39
 * @Description 确认订单项
 * @Version 1.0
 */
public class ComfirmOrderLine extends OrderLine {
    private String comfirmOrderId;


    private CartId cartId;


    public ComfirmOrderLine(String id, String comfirmOrderId, CartId cartId, Product product, ProductQuality quality) {
        super(id,product,quality);
        setComfirmOrderId(comfirmOrderId);
        setCartId(cartId);
    }





    public CartId getCartId() {
        return cartId;
    }






    public String getComfirmOrderId() {
        return comfirmOrderId;
    }


    public void setComfirmOrderId(String comfirmOrderId) {
        AssertUtils.notEmpty(comfirmOrderId,"comfirmOrderId must be not empty");
        this.comfirmOrderId = comfirmOrderId;
    }

    public void setCartId(CartId cartId) {
        AssertUtils.notNull(cartId,"cartId must be not Null");
        this.cartId = cartId;
    }


}
