package com.rc.cloud.common.security.service.impl.distributor;

import com.rc.cloud.app.distributor.user.DistributorUser;
import com.rc.cloud.app.distributor.user.RemoteDistributorUserService;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.service.RcUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Duration;

/**
 * @author WJF
 * @create 2023-07-11 9:27
 * @description TODO
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class RcDistributorUserDetailsServiceImpl extends AbstractRcDistributorUserDetailsServiceImpl {

    private static final String DISTRIBUTOR_NAME = "distributor";
    private static final String DISTRIBUTOR_APP_NAME = "rc_distributor";
    private final RemoteDistributorUserService remoteUserService;

    private final RedisTemplate<String, Object> redisTemplate;

    @SuppressWarnings("checkstyle:magicnumber")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userDetailsKey = CacheConstants.USER_DETAILS + "-" + DISTRIBUTOR_NAME + ":" + username;
        redisTemplate.setValueSerializer(RedisSerializer.java());
        RcUser rcUser = (RcUser) redisTemplate.opsForValue().get(userDetailsKey);
        if (rcUser != null && rcUser.getUsername() != null) {
            return rcUser;
        }

        CodeResult<DistributorUser> result = remoteUserService.userByMobile(username);
        UserDetails userDetails = getUserDetails(result);
        if (userDetails != null) {
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(userDetailsKey, userDetails, Duration.ofMinutes(5));
        }
        return userDetails;
    }


    /**
     * 是否支持此客户端校验(经销商系统手机号密码登录)
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    @Override
    public boolean support(String clientId, String grantType) {
        return DISTRIBUTOR_APP_NAME.equals(clientId) && SecurityConstants.PASSWORD.equals(grantType);
    }
}
