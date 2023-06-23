package com.rc.cloud.common.security.service;

import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.feign.RemoteUserService;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.web.CodeResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;

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

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		String userDetailsKey = CacheConstants.USER_DETAILS + ":" + username;
		String userDetailsStr = stringRedisTemplate.opsForValue().get(userDetailsKey);
		RcUser rcUser = JSONObject.parseObject(userDetailsStr, RcUser.class);

		if (userDetailsStr != null && rcUser.getUsername() != null) {
			return rcUser;
		}

		CodeResult<UserInfo> result = remoteUserService.info(username);
		UserDetails userDetails = getUserDetails(result);
		if (userDetails != null) {
			stringRedisTemplate.opsForValue().set(userDetailsKey, JSONObject.toJSONString(userDetails));
		}
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
