package com.rc.cloud.app.marketing.appearance.api.req.convert;

import com.rc.cloud.app.marketing.appearance.api.req.DeliveryAddressCreateRequest;
import com.rc.cloud.app.marketing.application.dto.DeliveryAddressCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * @Interface DeliveryAddressCreateRequestConvert
 * @Author liandy
 * @Date 2023/8/7 13:55
 * @Description  收货地址新建请求对象转换数据传输对象
 * @Version 1.0
 */
@Mapper
public interface DeliveryAddressCreateRequestConvert {
    DeliveryAddressCreateRequestConvert INSTANCE = Mappers.getMapper(DeliveryAddressCreateRequestConvert.class);


    DeliveryAddressCreateDTO convert(DeliveryAddressCreateRequest request);
}
