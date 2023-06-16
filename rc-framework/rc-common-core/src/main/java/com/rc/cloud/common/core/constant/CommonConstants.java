package com.rc.cloud.common.core.constant;

/**
 * 通用常量类
 *
 * @author hqf@rc
 * @date 2021-05-21
 **/
public interface CommonConstants {

    /**
     * 用户ID
     */
    String USER_ID = "userId";

    /**
     * 用户名
     */
    String USER_NAME = "username";

    /**
     * 租户ID
     */
    String TENANT_ID = "tenant_id";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * Authorization
     */
    String AUTH_KEY = "Authorization";

    /**
     * Bearer
     */
    String AUTHORIZATION_PREFIX = "Bearer";

    /**
     * 请求开始时间
     */
    String REQUEST_START_TIME = "REQUEST-START-TIME";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 失败标记
     */
    Integer FAIL = 1;

    /**
     * 当前页
     */
    String CURRENT = "current";

    /**
     * size
     */
    String SIZE = "size";
}
