package com.rc.cloud.app.system.api.oauthclient.vo;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @author rc@hqf
 * @since 2023/07/07
 */
@Data
public class SysOauthClientDetailsVO extends BaseDO {

	private String clientId;

	private String clientSecret;

	private String resourceIds;

	private String scope;

	private String authorizedGrantTypes;

	private String webServerRedirectUri;

	private String authorities;

	private Integer accessTokenValidity;

	private Integer refreshTokenValidity;

	private String additionalInformation;

	private String autoapprove;
}

