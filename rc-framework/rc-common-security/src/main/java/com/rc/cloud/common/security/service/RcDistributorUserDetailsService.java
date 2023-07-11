package com.rc.cloud.common.security.service;

import com.rc.cloud.app.distributor.user.DistributorUser;
import com.rc.cloud.app.distributor.user.RemoteDistributorUserService;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.web.CodeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author WJF
 * @create 2023-07-11 9:27
 * @description TODO
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class RcDistributorUserDetailsService implements RcUserDetailsService {

    private final String DISTRIBUTOR_NAME = "distributor";
    private final RemoteDistributorUserService remoteUserService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userDetailsKey = CacheConstants.USER_DETAILS + "-" + DISTRIBUTOR_NAME + ":" + username;
        redisTemplate.setValueSerializer(RedisSerializer.java());
        RcUser rcUser = (RcUser) redisTemplate.opsForValue().get(userDetailsKey);
        if (rcUser != null && rcUser.getUsername() != null) {
            return rcUser;
        }

        CodeResult<DistributorUser> result = remoteUserService.userByMobile(username);
        UserDetails userDetails = getUserDetails2(result);
        if (userDetails != null) {
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(userDetailsKey, userDetails, Duration.ofMinutes(5));
        }
        return userDetails;
    }

    public UserDetails getUserDetails2(CodeResult<DistributorUser> result) {
        DistributorUser userInfo = result.getData();
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList();
        // 构造security用户
        return new RcUser(userInfo.getId(), null, userInfo.getName(),
                userInfo.getPassword(), userInfo.getMobile(), true, true, true,
                true, authorities);
    }
}
