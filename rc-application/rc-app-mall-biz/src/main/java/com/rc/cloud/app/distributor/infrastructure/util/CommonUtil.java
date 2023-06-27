package com.rc.cloud.app.distributor.infrastructure.util;

import com.rc.cloud.common.core.util.StringUtils;

/**
 * @author WJF
 * @create 2023-06-27 16:52
 * @description TODO
 */

public class CommonUtil {

    //获取手机号后6位
    public static String getFinalMobile(String mobile){
        return StringUtils.substring(mobile,5,12);
    }
}
