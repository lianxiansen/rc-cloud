/**
 * @author oliveoil
 * date 2023-06-07 13:32
 */
package com.rc.cloud.app.system.biz.common.cache;

public class RedisKeys {

    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return "sys:captcha:" + key;
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String username) {
        return "sys:access_token:" + username;
    }

//    /**
//     * userInfo Key
//     */
//    public static String getUserInfoTokenKey(String username) {
//        return "sys:userinfo:" + username;
//    }

    /**
     * refreshToken Key
     */
    public static String getRefreshTokenKey(String username) {
        return "sys:refresh_token:" + username;
    }

}
