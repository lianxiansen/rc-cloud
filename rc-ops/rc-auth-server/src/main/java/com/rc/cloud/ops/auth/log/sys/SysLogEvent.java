package com.rc.cloud.ops.auth.log.sys;

import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author rc@hqf
 * @date 2023/07/20
 * @description 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(LoginLogCreateReqDTO source) {
        super(source);
    }
}
