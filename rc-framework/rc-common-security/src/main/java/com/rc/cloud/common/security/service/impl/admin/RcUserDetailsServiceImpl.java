package com.rc.cloud.common.security.service.impl.admin;

import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.feign.RemoteUserService;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.service.RcUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author oliveoil
 */
@Slf4j
@RequiredArgsConstructor
public class RcUserDetailsServiceImpl extends AbstractRcUserDetailsServiceImpl {

    private final RemoteUserService remoteUserService;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String ADMIN_CLIENT_NAME = "rc_admin";

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        String userDetailsKey = CacheConstants.USER_DETAILS + ":" + username;
        redisTemplate.setValueSerializer(RedisSerializer.java());
        RcUser rcUser = (RcUser) redisTemplate.opsForValue().get(userDetailsKey);
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

    /**
     * 后台管理账号密码登录
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    @Override
    public boolean support(String clientId, String grantType) {
        return ADMIN_CLIENT_NAME.equals(clientId) && SecurityConstants.PASSWORD.equals(grantType);
    }
}
