package com.rc.cloud.ops.auth.support.handler;

import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.app.system.enums.login.LoginLogTypeEnum;
import com.rc.cloud.app.system.enums.login.LoginResultEnum;
import com.rc.cloud.common.core.util.SpringContextHolder;
import com.rc.cloud.ops.auth.log.sys.SysLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author rc@hqf
 * @date 2023-06-02
 * <p>
 * @description 事件机制处理退出相关
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RcLogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            handle(authentication);
        }
    }

    /**
     * 处理退出成功方法
     * <p>
     * 获取到登录的authentication 对象
     *
     * @param authentication 登录对象
     */
    public void handle(Authentication authentication) {

        log.info("用户：{} 退出成功", authentication.getPrincipal());
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
//        String token = authorization.split(" ")[1];
//        redisTemplate.setValueSerializer(RedisSerializer.java());
//        OAuth2Authorization oAuth2Authorization = (OAuth2Authorization) redisTemplate.opsForValue()
//                .get("token:access_token:" + token);
//        String clientId = oAuth2Authorization.getRegisteredClientId();
//        if (clientId.equals(SecurityConstants.ADMIN_CLIENT_NAME)) {
//            recordSysLoginLog(authentication.getName());
//        }
    }

    private static void recordSysLoginLog(String username) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setUsername(username);
        reqDTO.setLogType(LoginLogTypeEnum.LOGOUT_SELF.getType());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        SpringContextHolder.publishEvent(new SysLogEvent(reqDTO));
    }
}
