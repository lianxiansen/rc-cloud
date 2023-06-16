package com.rc.cloud.app.system.service.user;

import com.rc.cloud.app.system.api.user.model.SysUserDO;
import org.springframework.security.core.userdetails.UserDetails;

public interface SysUserDetailsService {

    /**
     * 获取 UserDetails 对象
     */
    UserDetails getUserDetails(SysUserDO sysUserDO);
}
