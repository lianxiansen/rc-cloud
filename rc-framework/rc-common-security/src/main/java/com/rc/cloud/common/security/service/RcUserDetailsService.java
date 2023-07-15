package com.rc.cloud.common.security.service;

import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author oliveoil
 * @date 2023/06/16
 */
public interface RcUserDetailsService extends UserDetailsService, Ordered {

    /**
     * 是否支持此客户端校验
     *
     * @param clientId 目标客户端
     * @param grantType 授权类型
     * @return true/false
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值 默认取最大的
     *
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }

    /**
     * 通过用户实体查询
     *
     * @param rcUser user
     * @return UserDetails
     */
    default UserDetails loadUserByUser(RcUser rcUser) {
        return this.loadUserByUsername(rcUser.getUsername());
    }

}
