package com.rc.cloud.common.security.utils;

import cn.hutool.core.text.CharSequenceUtil;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.security.service.RcUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 *
 * @author L.cm
 */
@UtilityClass
public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	public RcUser getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof RcUser) {
			return (RcUser) principal;
		}
		return null;
	}

	public String getUsername() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return authentication.getName();
	}

	/**
	 * 获取用户
	 */
	public RcUser getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 * @return 角色集合
	 */
	public List<String> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roleIds = new ArrayList<>();
		authorities.stream()
			.filter(granted -> CharSequenceUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
			.forEach(granted -> {
				String id = CharSequenceUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
				roleIds.add(id);
			});
		return roleIds;
	}

}
