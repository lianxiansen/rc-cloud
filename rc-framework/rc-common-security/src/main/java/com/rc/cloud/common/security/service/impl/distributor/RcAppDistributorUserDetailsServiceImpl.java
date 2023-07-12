package com.rc.cloud.common.security.service.impl.distributor;

import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.security.service.RcUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author WJF
 * @create 2023-07-11 13:19
 * @description TODO
 */

public class RcAppDistributorUserDetailsServiceImpl extends AbstractRcDistributorUserDetailsServiceImpl{

    private final String DISTRIBUTOR_APP_NAME = "rc_distributor";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
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
