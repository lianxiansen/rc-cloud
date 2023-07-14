package com.rc.cloud.app.system.api.oauthclient.vo;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;

/**
 * @author rc@hqf
 * @date 2023/07/07
 * @description 客户端信息
 */
@Data
public class SysOauthClientDetailsVO extends BaseDO {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 资源ID列表
     */
    private String resourceIds;

    /**
     * 作用域
     */
    private String scope;

    /**
     * 授权方式
     */
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    private String webServerRedirectUri;

    /**
     * 权限列表
     */
    private String authorities;

    /**
     * 请求令牌有效时间
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     */
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    private String additionalInformation;

    /**
     * 是否自动授权
     */
    private String autoapprove;
}

