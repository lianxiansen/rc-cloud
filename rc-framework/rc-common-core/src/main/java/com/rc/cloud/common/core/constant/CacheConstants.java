
package com.rc.cloud.common.core.constant;

/**
 * @author oliveoil
 * date 2023-06-16 11:02
 * 缓存的key 常量
 */
public interface CacheConstants {

    /**
     * 用户信息缓存
     */
    String USER_DETAILS = "user_details";

    /**
     * oauth 缓存前缀
     */
    String PROJECT_OAUTH_ACCESS = "token::access_token";

    /**
     * oauth 客户端信息
     */
    String CLIENT_DETAILS_KEY = "client:details";

    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";
}
