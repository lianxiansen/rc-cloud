package com.rc.cloud.app.system.api.tenant.feign;

import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/05
 * @description 通过feign远程调用租户服务
 */
@FeignClient(contextId = "remoteTenantService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteTenantService {

    /**
     * 获取租户id列表
     *
     * @return 租户id列表
     */
    @GetMapping(value = "/sys/tenant/getTenantIdList", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult<List<String>> getTenantIdList();

    /**
     * 校验租户是否存在
     *
     * @param id 租户id
     * @return 是否成功
     */
    @GetMapping(value = "/sys/tenant/validateTenant", headers = SecurityConstants.HEADER_FROM_IN)
    CodeResult validateTenant(String id);
}
