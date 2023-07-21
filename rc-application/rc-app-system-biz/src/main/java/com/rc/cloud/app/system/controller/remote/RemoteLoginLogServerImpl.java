
package com.rc.cloud.app.system.controller.remote;

import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.logger.LoginLogService;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author oliveoil
 * @date 2023-07-19 13:47
 * @description 登录日志远程调用实现
 */
@RestController
@RequestMapping("/sys/login-log")
@Validated
public class RemoteLoginLogServerImpl {

    @Resource
    private LoginLogService loginLogService;

    /**
     * 记录登录日志
     *
     * @param reqDTO 登录日志DTO
     */
    @Inner
    @PostMapping("/save-log")
    public void saveLog(@Valid @RequestBody LoginLogCreateReqDTO reqDTO) {
        loginLogService.createLoginLog(reqDTO);
    }
}
