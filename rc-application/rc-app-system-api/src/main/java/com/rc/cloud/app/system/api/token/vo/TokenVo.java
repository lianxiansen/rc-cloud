package com.rc.cloud.app.system.api.token.vo;

import lombok.Data;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description 前端展示令牌管理
 */
@Data
public class TokenVo {

    /**
     * 令牌id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 令牌
     */
    private String accessToken;

    /**
     * 允许时间
     */
    private String issuedAt;

    /**
     * 过期时间
     */
    private String expiresAt;
}
