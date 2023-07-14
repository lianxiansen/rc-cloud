/**
 * @author rc@hqf
 * date 2023-07-11 16:50
 */
package com.rc.cloud.app.system.vo.client;

import com.rc.cloud.common.core.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:49
 * @description 客户端分页 Request VO
 */
@Schema(description = "管理后台 - 客户端分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OauthClientDetailsPageReqVO extends PageParam {

    /**
     * ID
     */
    @Schema(description = "id")
    private String id;

    /**
     * 客户端ID
     */
    @Schema(description = "客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 资源ID
     */
    @Schema(description = "资源id列表")
    private String resourceIds;

    /**
     * 作用域
     */
    @Schema(description = "作用域")
    private String scope;

    /**
     * 授权方式（A,B,C）
     */
    @Schema(description = "授权方式")
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @Schema(description = "权限列表")
    private String authorities;

    /**
     * 请求令牌有效时间
     */
    @Schema(description = "请求令牌有效时间")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     */
    @Schema(description = "刷新令牌有效时间")
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    @Schema(description = "扩展信息")
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    private String autoapprove;
}
