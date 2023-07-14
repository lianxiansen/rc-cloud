package com.rc.cloud.app.distributor.infrastructure.util;

import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.validation.ValidationUtils;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author WJF
 * @create 2023-06-27 16:52
 * @description 工具类
 */

public class CommonUtil {
    private static final int MOBILE_START = 5;
    private static final int MOBILE_END = 12;

    /**
     * 获取手机号后6位
     * @param mobile 手机号
     * @return 手机号后6位
     */
    public static String getFinalMobile(String mobile) {
        if (!ValidationUtils.isMobile(mobile)) {
            throw exception(DistributorErrorCodeConstants.MOBILE_PATTERN_NOT_CORRECT);
        }
        return StringUtils.substring(mobile, MOBILE_START, MOBILE_END);
    }
}
