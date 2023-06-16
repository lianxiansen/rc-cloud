package com.rc.cloud.ops.auth.remote;


import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.feign.FeignRequestInterceptor;
import com.rc.cloud.ops.auth.dto.AuthenticationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 认证服务接口
 *
 * @author haoxin
 * @date 2021-06-23
 **/
@FeignClient(
        contextId = "authService",
        value = ServiceNameConstants.AUTH_SERVICE,    // 服务名称
        configuration = FeignRequestInterceptor.class   // 请求拦截器 （关键代码）
//        fallbackFactory = FileApiServiceFallbackFactory.class     // 服务降级处理
)
public interface AuthenticationService {

    /**
     * 验证验证码
     * @return
     */
    @GetMapping({"/sys/validate-captcha/{uuid}/{captchaCode}"})
    boolean validateCaptcha(@PathVariable("uuid") String var1, @PathVariable("captchaCode") String var2);

    /**
     * 认证
     * @return
     */
    @GetMapping({"/sys/login-by-username/{userName}"})
    AuthenticationDTO loginByUserName(@PathVariable("userName") String var1);
}