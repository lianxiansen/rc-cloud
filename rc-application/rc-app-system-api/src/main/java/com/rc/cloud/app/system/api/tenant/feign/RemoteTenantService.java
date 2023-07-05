package com.rc.cloud.app.system.api.tenant.feign;

import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author oliveoil
 * @date 2023-07-05
 */
@FeignClient(contextId = "remoteTenantService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteTenantService {

	@GetMapping(value = "/sys/tenant/getTenantIdList", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<List<Long>> getTenantIdList();


	@GetMapping(value = "/sys/tenant/validateTenant", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult validateTenant(Long id);

}
