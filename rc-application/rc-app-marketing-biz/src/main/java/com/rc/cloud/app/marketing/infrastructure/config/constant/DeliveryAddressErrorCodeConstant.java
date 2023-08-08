package com.rc.cloud.app.marketing.infrastructure.config.constant;

import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @ClassName: DeliveryAddressErrorCodeConstant
 * @Author: liandy
 * @Date: 2023/7/12 09:26
 * @Description:收货地址错误码
 */
public interface DeliveryAddressErrorCodeConstant {
    ErrorCode NAME_NOT_EMPTY = new ErrorCode(1003001001, "姓名不为空");
    ErrorCode MOBILE_NOT_EMPTY = new ErrorCode(1003001002, "手机号不为空");
    ErrorCode ZIPCODE_NOT_EMPTY = new ErrorCode(1003001003, "邮政编号不为空");
    ErrorCode PROVINCE_CODE_NOT_EMPTY = new ErrorCode(1003001004, "地区-省编码不为空");
    ErrorCode PROVINCE_NOT_EMPTY = new ErrorCode(1003001005, "地区-省不为空");
    ErrorCode CITY_CODE_NOT_EMPTY = new ErrorCode(1003001006, "地区-市编码不为空");
    ErrorCode CITY_NOT_EMPTY = new ErrorCode(1003001007, "地区-市不为空");
    ErrorCode DISTRICT_CODE_NOT_EMPTY = new ErrorCode(1003001008, "地区-区编码不为空");
    ErrorCode DISTRICT_NOT_EMPTY = new ErrorCode(1003001009, "地区-区不为空");
    ErrorCode DETAIL_NOT_EMPTY = new ErrorCode(1003001010, "详细地址不为空");
    ErrorCode ID_NOT_EMPTY = new ErrorCode(1003001011, "唯一标识不为空");
    ErrorCode ID_INVALID = new ErrorCode(1003001012, "唯一标识无效");
}


