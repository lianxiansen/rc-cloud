package com.rc.cloud.app.system.enums.oauth2;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description OAuth2 授权类型（模式）的枚举
 */
@AllArgsConstructor
@Getter
public enum OAuth2GrantTypeEnum {

    /**
     * 密码模式
     */
    PASSWORD("password"),

    /**
     * 授权码模式
     */
    AUTHORIZATION_CODE("authorization_code"),

    /**
     * 简化模式
     */
    IMPLICIT("implicit"),

    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS("client_credentials"),

    /**
     * 刷新模式
     */
    REFRESH_TOKEN("refresh_token");

    /**
     * 授权类型
     */
    private final String grantType;

    /**
     * 根据授权类型获取枚举
     *
     * @param grantType 授权类型
     * @return OAuth2GrantTypeEnum
     */
    public static OAuth2GrantTypeEnum getByGranType(String grantType) {
        return ArrayUtil.firstMatch(o -> o.getGrantType().equals(grantType), values());
    }

}
