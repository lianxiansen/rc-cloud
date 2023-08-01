package com.rc.cloud.app.marketing.application.bo;

import com.rc.cloud.app.marketing.domain.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.deliveryaddress.DeliveryAddress;
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
    private ComfirmOrder comfirmOrder;
    private DeliveryAddress deliveryAddress;

    public ComfirmOrderBO(ComfirmOrder comfirmOrder, DeliveryAddress deliveryAddress) {
    }
}
