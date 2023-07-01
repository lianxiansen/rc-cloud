package com.rc.cloud.app.distributor.infrastructure.util;

import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.common.core.util.StringUtils;

import java.util.regex.Pattern;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author WJF
 * @create 2023-06-27 16:52
 * @description TODO
 */

public class CommonUtil {
    public final static String MOBILE_PATTERN = "^1[3-9]\\d{9}$";

    public static Boolean isMobile(String str) {
        return Pattern.matches(MOBILE_PATTERN, str);
    }

    //获取手机号后6位
    public static String getFinalMobile(String mobile) {
        if (!isMobile(mobile)){
            throw exception(DistributorErrorCodeConstants.MOBILE_PATTERN_NOT_CORRECT);
        }
        return StringUtils.substring(mobile, 5, 12);
    }
}
