package com.rc.cloud.app.distributor.user;

import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author WJF
 * @create 2023-07-11 10:18
 * @description TODO
 */

@FeignClient(contextId = "remoteDistributorUserService", value = ServiceNameConstants.DISTRIBUTOR_SERVICE)
public interface RemoteDistributorUserService {

    /**
     * 通过用户ID查询用户信息
     * @param id 用户ID
     * @return CodeResult
     */
    @GetMapping(value = "/distributor/user/getById/{id}", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<DistributorUser> userById(@PathVariable("id") String id);


    /**
     * 通过手机号码查询用户、角色信息
     * @param mobile 手机号码
     * @return R
     */
    @GetMapping(value = "/distributor/user/getByMobile/{mobile}", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<DistributorUser> userByMobile(@PathVariable("mobile") String mobile);
}
