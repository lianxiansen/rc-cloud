package com.rc.cloud.common.security.service.impl.distributor;

import com.rc.cloud.app.distributor.user.DistributorUser;
import com.rc.cloud.app.distributor.user.RemoteDistributorUserService;
import com.rc.cloud.common.core.constant.CacheConstants;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.service.RcUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author WJF
 * @create 2023-07-11 13:19
 * @description TODO
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class RcAppDistributorUserDetailsServiceImpl extends AbstractRcDistributorUserDetailsServiceImpl {

    private static final String DISTRIBUTOR_APP_NAME = "rc_distributor";

    private final CacheManager cacheManager;

    private final RemoteDistributorUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (cache != null && cache.get(phone) != null) {
            return (RcUser) cache.get(phone).get();
        }

        CodeResult<DistributorUser> result = remoteUserService.userByMobile(phone);
        UserDetails userDetails = getUserDetails(result);
        if (cache != null) {
            cache.put(phone, userDetails);
        }
        return userDetails;
    }

    /**
     * 是否支持此客户端校验(经销商系统手机号验证登录)
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    @Override
    public boolean support(String clientId, String grantType) {

        return DISTRIBUTOR_APP_NAME.equals(clientId) && SecurityConstants.MOBILE.equals(grantType);
    }

}
