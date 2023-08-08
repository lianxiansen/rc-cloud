package com.rc.cloud.app.marketing.application.bo.convert;

import com.rc.cloud.app.marketing.application.bo.DeliveryAddressBO;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-25 11:23
 * @description TODO
 */
@Mapper
public interface DeliveryAddressConvert {
    DeliveryAddressConvert INSTANCE = Mappers.getMapper(DeliveryAddressConvert.class);


    /**
     * 领域对象转为业务对象
     * @param deliveryAddress
     * @return
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "province", source = "area.province")
    @Mapping(target = "provinceCode", source = "area.provinceCode")
    @Mapping(target = "city", source = "area.city")
    @Mapping(target = "cityCode", source = "area.cityCode")
    @Mapping(target = "district", source = "area.district")
    @Mapping(target = "districtCode", source = "area.districtCode")
    @Mapping(target = "detail", source = "area.detail")
    @Mapping(target = "defaulted", source = "defaulted")
    DeliveryAddressBO convert(DeliveryAddress deliveryAddress);



    List<DeliveryAddressBO> convert(List<DeliveryAddress> deliveryAddresses);
}
