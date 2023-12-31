package com.rc.cloud.app.system.api.logger.feign;

import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author rc@hqf
 * @date 2023/07/19
 * @description 通过feign远程调用登录日志服务
 */
@FeignClient(contextId = "remoteLoginLogService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteLoginLogService {

    /**
     * 记录登录日志
     *
     * @param reqDTO 登录日志DTO
     */
    @PostMapping(value = "/sys/login-log/save-log", headers = SecurityConstants.HEADER_FROM_IN)
    void saveLog(@Valid @RequestBody LoginLogCreateReqDTO reqDTO);
}
