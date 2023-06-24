package com.rc.cloud.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
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
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(CodeResult<UserInfo> result) {
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
		SysUserDO user = userInfo.getSysUser();

		// 构造security用户
		return new RcUser(user.getId(), user.getDeptId(), user.getUsername(),
				user.getPassword(), user.getMobile(), true, true, true,
				(user.getStatus().equals(CommonStatusEnum.ENABLE.getStatus())), authorities);
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
