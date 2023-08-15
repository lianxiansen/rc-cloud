package com.rc.cloud.app.marketing.appearance.api.resp.convert;

import com.rc.cloud.app.marketing.appearance.api.resp.PlaceOrderResp;
import com.rc.cloud.app.marketing.application.bo.ComfirmOrderBO;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;

import java.util.Objects;

/**
 * @ClassName PlaceOrderRespConvert
 * @Author liandy
 * @Date 2023/8/15 16:29
 * @Description TODO
 * @Version 1.0
 */
public class PlaceOrderRespConvert {
    public static PlaceOrderResp convert(ComfirmOrderBO bo){
        PlaceOrderResp resp=new PlaceOrderResp();
        if(Objects.nonNull(bo.getDeliveryAddress())){
            resp.setDeliveryAddressId(bo.getDeliveryAddress().getId())
                    .setCustomerName(bo.getDeliveryAddress().getName())
                    .setCustomerMobile(bo.getDeliveryAddress().getMobile());
            if(Objects.nonNull(bo.getDeliveryAddress().getArea())){
                Area area=bo.getDeliveryAddress().getArea();
                resp.setProvinceCode(area.getProvinceCode())
                        .setProvince(area.getProvince())
                        .setCityCode(area.getCityCode())
                        .setCity(area.getCity())
                        .setDistrictCode(area.getDistrictCode())
                        .setDistrict(area.getDistrict())
                        .setDeliveryAddressDetail(area.getDetail());
            }
        }
        //TODO liandy
        return null;
    }
}
