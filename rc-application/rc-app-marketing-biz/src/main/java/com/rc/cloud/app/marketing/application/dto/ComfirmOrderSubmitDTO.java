package com.rc.cloud.app.marketing.application.dto;

import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import lombok.Data;

/**
 * @ClassName SubmitComfirmOrderDTO
 * @Author liandy
 * @Date 2023/8/15 08:15
 * @Description 确认单提交数据传输对象
 * @Version 1.0
 */
@Data
public class ComfirmOrderSubmitDTO {
    /**
     * 确认订单唯一标识
     */
    private String comfirmOrderId;
    /**
     * 收货地址唯一标识
     */
    private String deliveryAddressId;
    /**
     * 交易类型
     */
    private Integer tradeType;

    /**
     * 配送类型
     * @see DeliveryType
     */
    private Integer deliveryType;

    /**
     * 订单备注
     */
    private String note;
}
