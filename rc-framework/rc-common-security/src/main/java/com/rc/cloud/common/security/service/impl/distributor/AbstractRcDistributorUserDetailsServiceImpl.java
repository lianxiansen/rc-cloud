package com.rc.cloud.common.security.service.impl.distributor;

import com.rc.cloud.app.distributor.user.DistributorUser;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.service.RcUser;
import com.rc.cloud.common.security.service.RcUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

/**
 * @author WJF
 * @create 2023-07-11 14:17
 * @description TODO
 */

public abstract class AbstractRcDistributorUserDetailsServiceImpl implements RcUserDetailsService {
    public UserDetails getUserDetails(CodeResult<DistributorUser> result) {
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
