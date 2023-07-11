package com.rc.cloud.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.vo.SysUserVO;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author oliveoil
 * @date 2023/06/16
 */
public interface RcUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 通过用户实体查询
	 * @param rcUser user
	 * @return
	 */
	default UserDetails loadUserByUser(RcUser rcUser) {
		return this.loadUserByUsername(rcUser.getUsername());
	}

}
