package com.rc.cloud.app.system.api.user.feign;

import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserService {

	/**
	 * 通过用户ID查询用户信息
	 * @param id 用户ID
	 * @return CodeResult
	 */
	@PostMapping(value = "/sys/user/info-by-ids", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<UserInfo> infoByIds(@RequestParam("ids") List<Long> ids);

	/**
	 * 通过用户ID查询用户信息
	 * @param id 用户ID
	 * @return CodeResult
	 */
	@GetMapping(value = "/sys/user/info-by-id/{id}", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<UserInfo> infoById(@PathVariable("id") Long id);

	/**
	 * 通过用户名查询用户、角色信息
	 * @param username 用户名
	 * @return CodeResult
	 */
	@GetMapping(value = "/sys/user/info/{username}", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<UserInfo> info(@PathVariable("username") String username);

	/**
	 * 通过手机号码查询用户、角色信息
	 * @param mobile 手机号码
	 * @return R
	 */
	@GetMapping(value = "/sys/app/info/{mobile}", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<UserInfo> infoByMobile(@PathVariable("mobile") String mobile);

	/**
	 * 根据部门id，查询对应的用户 id 集合
	 * @param deptIds 部门id 集合
	 * @return 用户 id 集合
	 */
	@GetMapping(value = "/sys/user/ids", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds);

}
