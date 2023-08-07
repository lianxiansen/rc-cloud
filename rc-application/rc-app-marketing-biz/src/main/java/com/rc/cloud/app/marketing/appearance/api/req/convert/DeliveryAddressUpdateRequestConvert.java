package com.rc.cloud.app.marketing.appearance.api.req.convert;

import com.rc.cloud.app.marketing.appearance.api.req.DeliveryAddressUpdateRequest;
import com.rc.cloud.app.marketing.application.dto.DeliveryAddressUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Interface DeliveryAddressUpdateRequestConvert
 * @Author liandy
 * @Date 2023/8/7 14:01
 * @Description  收货地址更新请求对象转换数据传输对象
 * @Version 1.0
 */
@Mapper
public interface DeliveryAddressUpdateRequestConvert {
    DeliveryAddressUpdateRequestConvert INSTANCE = Mappers.getMapper(DeliveryAddressUpdateRequestConvert.class);


    DeliveryAddressUpdateDTO convert(DeliveryAddressUpdateRequest request);
}
