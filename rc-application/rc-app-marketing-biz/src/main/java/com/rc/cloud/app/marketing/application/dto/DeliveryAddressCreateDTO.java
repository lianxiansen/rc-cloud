package com.rc.cloud.app.marketing.application.dto;

import com.rc.cloud.app.marketing.infrastructure.config.constant.DeliveryAddressErrorCodeConstant;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import lombok.Data;

/**
 * @ClassName DeliveryAddressDTO
 * @Author liandy
 * @Date 2023/8/3 14:17
 * @Description 收货地址创建数据传输对象
 * @Version 1.0
 */
@Data
public class DeliveryAddressCreateDTO {

    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮政编号
     */
    private String zipcode;
    /**
     * 省
     */
    private String province;

    private String provinceCode;

    /**
     * 市
     */
    private String city;

    private String cityCode;
    /**
     * 区
     */
    private String district;

    private String districtCode;
    /**
     * 详细地址
     */
    private String detail;
    /**
     * 是否为默认 0：不是 1：是
     */
    private Boolean defaulted;

    /**
     * 校验实例字段，抛出ServiceException异常
     */
    public void validate() {
        if(StringUtils.isEmpty(getName())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.NAME_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getMobile())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.MOBILE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getZipcode())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.ZIPCODE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getProvinceCode())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.PROVINCE_CODE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getProvince())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.PROVINCE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getCityCode())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.CITY_CODE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getCity())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.CITY_NOT_EMPTY);
        }

        if(StringUtils.isEmpty(getDistrictCode())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.DISTRICT_CODE_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getDistrict())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.DISTRICT_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(getDetail())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.DETAIL_NOT_EMPTY);
        }
    }
}
