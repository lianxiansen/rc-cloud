package com.rc.cloud.app.dubbo.application;

import com.rc.cloud.app.dubbo.application.service.AuthenticationService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @ClassName AuthenticationServiceImpl
 * @Author liandy
 * @Date 2023/7/26 09:19
 * @Description TODO
 * @Version 1.0
 */
@DubboService
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public String validateCaptcha(String uuid, String captchaCode) {
        return uuid+captchaCode;
    }
}
