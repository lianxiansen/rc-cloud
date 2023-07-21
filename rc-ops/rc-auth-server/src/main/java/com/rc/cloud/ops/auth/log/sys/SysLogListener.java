package com.rc.cloud.ops.auth.log.sys;

import cn.hutool.extra.servlet.ServletUtil;
import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.app.system.api.logger.feign.RemoteLoginLogService;
import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.feign.RemoteUserService;
import com.rc.cloud.common.core.web.CodeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author rc@hqf
 * @date 2023/07/20
 * @description 异步监听日志事件
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SysLogListener {

    private final RemoteLoginLogService remoteLogService;
    private final RemoteUserService remoteUserService;
    private static final int USER_TYPE = 2;

    /**
     * 保存登录日志
     * @param event 登录日志事件
     */
    @Async
    @Order
    @EventListener
    public void saveSysLog(SysLogEvent event) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        LoginLogCreateReqDTO loginLog = (LoginLogCreateReqDTO) event.getSource();
        CodeResult<UserInfo> info = remoteUserService.info(loginLog.getUsername());
        loginLog.setUserId(info.getData().getSysUser().getId());
        loginLog.setUserIp(ServletUtil.getClientIP(request));
        loginLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        loginLog.setUserType(USER_TYPE);
        remoteLogService.saveLog(loginLog);
    }
}
