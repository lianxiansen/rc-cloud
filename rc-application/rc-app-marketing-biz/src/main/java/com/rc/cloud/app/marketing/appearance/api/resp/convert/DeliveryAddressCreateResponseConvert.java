package com.rc.cloud.app.marketing.appearance.api.resp.convert;

import com.rc.cloud.app.marketing.appearance.api.resp.DeliveryAddressResponse;
import com.rc.cloud.app.marketing.application.bo.DeliveryAddressBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * @Interface DeliveryAddressCreateResponseConvert
 * @Author liandy
 * @Date 2023/8/7 15:09
 * @Description  收货地址业务对象转response对象
 * @Version 1.0
 */
@Mapper
public interface DeliveryAddressCreateResponseConvert {
    DeliveryAddressCreateResponseConvert INSTANCE = Mappers.getMapper(DeliveryAddressCreateResponseConvert.class);
    DeliveryAddressResponse convert(DeliveryAddressBO bo);
    List<DeliveryAddressResponse> convert(List<DeliveryAddressBO> bos);
}
