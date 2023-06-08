package com.rc.cloud.app.system.biz.common.security.exception;

import com.rc.cloud.common.core.util.json.JsonUtils;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户(token不存在、错误)，异常处理器
 *
 * @author oliveoil
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 判断是否是刷新token的请求，如果是则返回状态码10052
        // 其他情况，返回状态码10051
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader(HttpHeaders.ORIGIN));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 暂时定义为
        response.getWriter().print(JsonUtils.toJsonString(CodeResult.fail(10041, "访问令牌失效")));
    }
}