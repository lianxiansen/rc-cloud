package com.rc.cloud.app.system.common.security.exception;

import com.rc.cloud.common.core.util.json.JsonUtils;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.rc.cloud.common.core.exception.enums.GlobalErrorCodeConstants.TOKEN_INVALID;

/**
 * 匿名用户(token不存在、错误)，异常处理器
 *
 * @author oliveoil
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader(HttpHeaders.ORIGIN));
        response.getWriter().print(JsonUtils.toJsonString(CodeResult.fail(TOKEN_INVALID.getCode(), TOKEN_INVALID.getMsg())));
    }
}