package com.rc.cloud.common.core.constant;

/**
 * 权限相关通用常量
 * 
 * @author hqf@rc
 */
public class SecurityConstants
{
    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 授权信息字段
     */
    public static final String AUTHORIZATION_HEADER = "authorization";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";

    /**
     * 用户标识
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * app
     */
    public static final String APP = "app";
    /**
     * role
     */
    public static final String ROLE = "role";
    /**
     * bcrypt
     */
    public static final String BCRYPT = "bcrypt";

    /**
     * 内部
     */
    public static final String FROM_IN = "Y";

    /**
     * 标志
     */
    public static final String FROM = "from";

    /**
     * 请求header
     */
    public static final String HEADER_FROM_IN = FROM + "=" + FROM_IN;

    /**
     * 项目的license
     */
    public static final String PROJECT_LICENSE = "https://rc.com";

    /**
     * 授权码模式confirm
     */
    public static final String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";

    /**
     * 用户信息
     */
    public static final String DETAILS_USER = "user_info";

    /**
     * 协议字段
     */
    public static final String DETAILS_LICENSE = "license";

    /**
     * 客户端ID
     */
    public static final String CLIENT_ID = "clientId";

    /**
     * 客户端模式
     */
    public static final String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * {noop} 加密的特征码
     */
    public static final String NOOP = "{noop}";
}
