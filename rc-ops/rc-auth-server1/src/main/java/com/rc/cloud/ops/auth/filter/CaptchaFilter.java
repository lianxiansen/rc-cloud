package com.rc.cloud.ops.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.common.core.constant.AuthConstants;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.ops.auth.remote.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 *
 * @author haoxin
 * @date 2021-06-23
 **/
@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService remoteAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        RequestMatcher matcher = new AntPathRequestMatcher(AuthConstants.OAUTH_TOKEN, HttpMethod.POST.toString());
        if (matcher.matches(httpServletRequest)
                && StringUtils.equalsIgnoreCase(httpServletRequest.getParameter(AuthConstants.GRANT_TYPE_KEY), AuthConstants.PASSWORD)) {
            String code = httpServletRequest.getParameter(AuthConstants.VALIDATE_CODE_CODE);
            String key = httpServletRequest.getParameter(AuthConstants.VALIDATE_CODE_KEY);

            if (!remoteAuthenticationService.validateCaptcha(key, code)) {
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.getOutputStream().write(JSONObject.toJSONString(CodeResult.fail("验证码不正确")).getBytes());
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
