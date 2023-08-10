package com.rc.cloud.api.product.service;

/**
 * @ClassName AuthenticationService
 * @Author liandy
 * @Date 2023/8/10 17:32
 * @Description TODO
 * @Version 1.0
 */
public interface AuthenticationService {
    String validateCaptcha(String uuid, String captchaCode);
}
