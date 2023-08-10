package com.rc.cloud.app.dubbo.application;

import com.rc.cloud.api.product.service.AuthenticationService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @ClassName AuthenticationServiceImpl
 * @Author liandy
 * @Date 2023/7/26 09:19
 * @Description TODO
 * @Version 1.0
 */
@DubboService(export = true)
@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public String validateCaptcha(String uuid, String captchaCode) {
        return uuid+captchaCode;
    }
}
