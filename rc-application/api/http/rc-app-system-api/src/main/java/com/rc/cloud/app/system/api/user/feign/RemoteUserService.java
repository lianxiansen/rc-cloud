package com.rc.cloud.app.system.api.user.feign;

import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.vo.SysUserInfoVO;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.feign.FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description 通过feign远程调用用户服务
 */
@FeignClient(
        contextId = "remoteUserService",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        configuration = FeignRequestInterceptor.class
)
public interface RemoteUserService {

    /**
     * 通过用户ID查询用户信息
     *
     * @param ids 用户ID列表
     * @return CodeResult
     */
    @PostMapping(value = "/sys/user/info-by-ids", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<List<SysUserInfoVO>> infoByIds(@RequestBody List<String> ids);

    /**
     * 通过用户ID查询用户信息
     *
     * @param id 用户ID
     * @return CodeResult
     */
    @GetMapping(value = "/sys/user/info-by-id/{id}", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<SysUserInfoVO> infoById(@PathVariable("id") String id);

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserInfo
     */
    @GetMapping(value = "/sys/user/info/{username}", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<UserInfo> info(@PathVariable("username") String username);

    /**
     * 通过手机号码查询用户、角色信息
     *
     * @param mobile 手机号码
     * @return UserInfo
     */
    @GetMapping(value = "/sys/app/info/{mobile}", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<UserInfo> infoByMobile(@PathVariable("mobile") String mobile);

    /**
     * 根据部门id，查询对应的用户 id 集合
     *
     * @param deptIds 部门id 集合
     * @return 用户 id 集合
     */
    @GetMapping(value = "/sys/user/ids", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<List<String>> listUserIdByDeptIds(@RequestParam("deptIds") Set<String> deptIds);

}
