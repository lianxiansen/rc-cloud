package com.rc.cloud.app.marketing.infrastructure.repository.convert;

import com.rc.cloud.app.marketing.domain.entity.common.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.infrastructure.repository.po.DeliveryAddressPO;

/**
 * @ClassName DeliveryAddressConvert
 * @Author liandy
 * @Date 2023/8/3 14:53
 * @Description 收货地址、po转换器
 * @Version 1.0
 */
public class DeliveryAddressConvert {
    public static DeliveryAddress toDeliveryAddress(DeliveryAddressPO po){
        Area area=new Area(po.getProvince(),po.getCity(),po.getDistrict(),po.getDetail());
        DeliveryAddress deliveryAddress=new DeliveryAddress(po.getId(),po.getCustomerId(),po.getUserName(),po.getMobile(),po.getZipcode(),area);
        deliveryAddress.setDefault(po.isDefaulted());
        return deliveryAddress;
    }

    public static DeliveryAddressPO toDeliveryAddressPO(DeliveryAddress entity){
        DeliveryAddressPO po = new DeliveryAddressPO();
        po.setId(entity.getId());
        po.setUserName(entity.getUserName());
        po.setMobile(entity.getMobile());
        po.setZipcode(entity.getZipcode());
        po.setProvince(entity.getArea().getProvince());
        po.setCity(entity.getArea().getCity());
        po.setDistrict(entity.getArea().getDistrict());
        po.setDetail(entity.getArea().getDetail());
        po.setDefaulted(entity.isDefaulted());
        return po;
    }
}
