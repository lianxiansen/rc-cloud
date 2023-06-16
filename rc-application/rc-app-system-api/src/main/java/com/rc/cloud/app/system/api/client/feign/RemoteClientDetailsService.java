package com.rc.cloud.app.system.api.client.feign;

import com.rc.cloud.app.system.api.client.model.SysOauthClientDetailsDO;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lengleng
 * @date 2020/12/05
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息
	 * @param clientId 用户名
	 * @return R
	 */
	@GetMapping(value = "/client/getClientDetailsById/{clientId}", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<SysOauthClientDetailsDO> getClientDetailsById(@PathVariable("clientId") String clientId);

	/**
	 * 查询全部客户端
	 * @return R
	 */
	@GetMapping(value = "/client/list", headers = SecurityConstants.HEADER_FROM_IN)
	CodeResult<List<SysOauthClientDetailsDO>> listClientDetails();

}
