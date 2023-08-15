package com.rc.cloud.app.marketing.application.bo;

import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.TradeType;
import lombok.Data;

/**
 * @ClassName ComfirmOrderBO
 * @Author liandy
 * @Date 2023/7/29 14:58
 * @Description TODO
 * @Version 1.0
 */
@Data
public class ComfirmOrderBO {
    private DeliveryAddress deliveryAddress;
    private ComfirmOrder comfirmOrder;
    private TradeType[] tradeTypes;
    private DeliveryType[] deliveryTypes;

    public ComfirmOrderBO(DeliveryAddress deliveryAddress, ComfirmOrder comfirmOrder, TradeType[] tradeTypes, DeliveryType[] deliveryTypes) {
        this.deliveryAddress = deliveryAddress;
        this.comfirmOrder = comfirmOrder;
        this.tradeTypes = tradeTypes;
        this.deliveryTypes = deliveryTypes;
    }
}
