/**
 * @author oliveoil
 * date 2023-07-11 13:55
 */
package com.rc.cloud.common.security.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.vo.SysUserVO;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.service.RcUser;
import com.rc.cloud.common.security.service.RcUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRcUserDetailsServiceImpl implements RcUserDetailsService {

    /**
     * 构建userdetails
     * @param result 用户信息
     * @return UserDetails
     */
    protected UserDetails getUserDetails(CodeResult<UserInfo> result) {
        UserInfo userInfo = result.getData();
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<String> dbAuthsSet = new HashSet<>();

        if (ArrayUtil.isNotEmpty(userInfo.getRoles())) {
            // 获取角色
            Arrays.stream(userInfo.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(userInfo.getPermissions()));
        }

        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUserVO user = userInfo.getSysUser();

        // 构造security用户
        return new RcUser(user.getId(), user.getDeptId(), user.getUsername(),
                user.getPassword(), user.getMobile(), true, true, true,
                (user.getStatus().equals(CommonStatusEnum.ENABLE.getStatus())), authorities);
    }

    @Override
    public boolean support(String clientId, String grantType) {
        return false;
    }
}
