package com.rc.cloud.common.security.service;

import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.feign.RemoteUserService;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.web.CodeResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author oliveoil
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class RcUserDetailsServiceImpl implements RcUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		String userDetailsKey = CacheConstants.USER_DETAILS + ":" + username;
		RcUser rcUser = (RcUser)redisTemplate.opsForValue().get(userDetailsKey);

		if (rcUser != null && rcUser.getUsername() != null) {
			return rcUser;
		}

		CodeResult<UserInfo> result = remoteUserService.info(username);
		UserDetails userDetails = getUserDetails(result);
		if (userDetails != null) {
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(userDetailsKey, userDetails);
		}
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
