package com.rc.cloud.common.security.service.impl;

import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.feign.RemoteUserService;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.service.RcUser;
import com.rc.cloud.common.security.service.RcUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@RequiredArgsConstructor
public class RcAppUserDetailsServiceImpl implements RcUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(phone) != null) {
			return (RcUser) cache.get(phone).get();
		}

		CodeResult<UserInfo> result = remoteUserService.infoByMobile(phone);

		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(phone, userDetails);
		}
		return userDetails;
	}

	/**
	 * check-token 使用
	 * @param rcUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(RcUser rcUser) {
		return this.loadUserByUsername(rcUser.getMobile());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {

		return SecurityConstants.APP.equals(grantType);
	}

}
