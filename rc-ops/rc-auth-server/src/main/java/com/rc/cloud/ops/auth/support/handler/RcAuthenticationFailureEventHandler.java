package com.rc.cloud.ops.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.app.system.enums.login.LoginLogTypeEnum;
import com.rc.cloud.app.system.enums.login.LoginResultEnum;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.util.MsgUtils;
import com.rc.cloud.common.core.util.SpringContextHolder;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.core.web.util.WebFrameworkUtils;
import com.rc.cloud.ops.auth.log.sys.SysLogEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author oliveoil
 * @date 2023-06-16
 */
@Slf4j
public class RcAuthenticationFailureEventHandler implements AuthenticationFailureHandler {

    private final MappingJackson2HttpMessageConverter errorHttpResponseConverter = new MappingJackson2HttpMessageConverter();

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     */
    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        String username = request.getParameter(OAuth2ParameterNames.USERNAME);
        log.info("用户：{} 登录失败，异常：{}", username, exception.getLocalizedMessage());
//        String clientId = WebFrameworkUtils.getClientId(request);
//        if (clientId.equals(SecurityConstants.ADMIN_CLIENT_NAME)) {
//            recordSysLoginLog(username);
//        }
        // 写出错误信息
        sendErrorResponse(request, response, exception);
    }

    // 记录sys的登录日志
    private static void recordSysLoginLog(String username) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setUsername(username);
        reqDTO.setLogType(LoginLogTypeEnum.LOGIN_USERNAME.getType());
        reqDTO.setResult(LoginResultEnum.ERROR.getResult());
        SpringContextHolder.publishEvent(new SysLogEvent(reqDTO));
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
                                   AuthenticationException exception) throws IOException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        String errorMessage;

        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2AuthenticationException authorizationException = (OAuth2AuthenticationException) exception;
            errorMessage = StrUtil.isBlank(authorizationException.getError().getDescription())
                    ? authorizationException.getError().getErrorCode()
                    : authorizationException.getError().getDescription();
        } else {
            errorMessage = exception.getLocalizedMessage();
        }

        // 手机号登录
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (SecurityConstants.MOBILE.equals(grantType)) {
            errorMessage = MsgUtils.getSecurityMessage("AbstractUserDetailsAuthenticationProvider.smsBadCredentials");
        }

        this.errorHttpResponseConverter.write(CodeResult.fail(errorMessage), MediaType.APPLICATION_JSON, httpResponse);
    }

}
