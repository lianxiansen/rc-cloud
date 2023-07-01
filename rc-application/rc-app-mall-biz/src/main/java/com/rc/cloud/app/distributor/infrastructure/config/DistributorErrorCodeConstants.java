package com.rc.cloud.app.distributor.infrastructure.config;

import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @author WJF
 * @create 2023-06-26 8:56
 * @description TODO
 */

public interface DistributorErrorCodeConstants {
    // ========== 经销商模版 1002029000 ==========
    ErrorCode DISTRIBUTOR_NOT_EXISTS = new ErrorCode(1002029000, "经销商不存在");
    ErrorCode DISTRIBUTOR_CHANNEL_NOT_EXISTS = new ErrorCode(1002029001, "经销商渠道不存在");
    ErrorCode DISTRIBUTOR_LEVEL_NOT_EXISTS = new ErrorCode(1002029002, "经销商等级不存在");
    ErrorCode DISTRIBUTOR_SOURCE_EXISTS = new ErrorCode(1002029003, "经销商来源不存在");
    ErrorCode DISTRIBUTOR_REPUTATION_NOT_EXISTS = new ErrorCode(1002029004, "经销商信誉不存在");
    ErrorCode DISTRIBUTOR_CONTACT_PHONE_DUPLICATE = new ErrorCode(1002029005, "联系方式重复");
    ErrorCode DISTRIBUTOR_CONTACT_PHONE_EXIST = new ErrorCode(1002029006, "该联系方式已被绑定");
    // ========== 手机 1002030000 ==========
    ErrorCode MOBILE_PATTERN_NOT_CORRECT = new ErrorCode(1002030001, "手机号格式不正确");
}
