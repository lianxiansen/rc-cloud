package com.rc.cloud.common.core.web.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.NumberUtil;
import com.rc.cloud.common.core.enums.UserTypeEnum;
import com.rc.cloud.common.core.exception.CheckedException;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.core.web.config.WebProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import static com.rc.cloud.common.core.exception.enums.GlobalErrorCodeConstants.NOT_FOUND_HTTP_SERVLET_REQUEST;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 专属于 web 包的工具类
 *
 * @author 芋道源码
 */
@RequiredArgsConstructor
public class WebFrameworkUtils {

    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";

    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

    public static final String HEADER_TENANT_ID = "tenant-id";
    private static final String BASIC_ = "Basic ";

    private static WebProperties properties;

    public WebFrameworkUtils(WebProperties webProperties) {
        WebFrameworkUtils.properties = webProperties;
    }

    /**
     * 获得租户编号，从 header 中
     * 考虑到其它 framework 组件也会使用到租户编号，所以不得不放在 WebFrameworkUtils 统一提供
     *
     * @param request 请求
     * @return 租户编号
     */
    public static String getTenantId(HttpServletRequest request) {
        String tenantId = request.getHeader(HEADER_TENANT_ID);
        if (tenantId == null) {
            tenantId = String.valueOf(request.getHeaders(HEADER_TENANT_ID));
        }
        return Objects.equals(tenantId, "") ? tenantId : null;
    }

    public static void setLoginUserId(ServletRequest request, String userId) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID, userId);
    }

    /**
     * 获取 HttpServletResponse
     * @return {HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        if (servletRequestAttributes == null) {
            throw exception(NOT_FOUND_HTTP_SERVLET_REQUEST);
        }
        return servletRequestAttributes.getResponse();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

//    /**
//     * 设置用户类型
//     *
//     * @param request 请求
//     * @param userType 用户类型
//     */
//    public static void setLoginUserType(ServletRequest request, Integer userType) {
//        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE, userType);
//    }

    /**
     * 获得当前用户的编号，从请求中
     * 注意：该方法仅限于 framework 框架使用！！！
     *
     * @param request 请求
     * @return 用户编号
     */
    public static String getLoginUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID).toString();
    }

    /**
     * 获得当前用户的类型
     * 注意：该方法仅限于 web 相关的 framework 组件使用！！！
     *
     * @param request 请求
     * @return 用户编号
     */
    public static Integer getLoginUserType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 1. 优先，从 Attribute 中获取
        Integer userType = (Integer) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE);
        if (userType != null) {
            return userType;
        }
        // 2. 其次，基于 URL 前缀的约定
        if (request.getRequestURI().startsWith(properties.getAdminApi().getPrefix())) {
            return UserTypeEnum.ADMIN.getValue();
        }
        if (request.getRequestURI().startsWith(properties.getAppApi().getPrefix())) {
            return UserTypeEnum.MEMBER.getValue();
        }
        return null;
    }

//    public static Integer getLoginUserType() {
//        HttpServletRequest request = getRequest();
//        return getLoginUserType(request);
//    }

    public static String getLoginUserId() {
        if (getRequest().isPresent()) {
            return getLoginUserId(getRequest().get());
        }
        return null;
    }

    public static void setCommonResult(ServletRequest request, CodeResult<?> result) {
        request.setAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT, result);
    }

    public static CodeResult<?> getCommonResult(ServletRequest request) {
        return (CodeResult<?>) request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
    }

//    public static HttpServletRequest getRequest() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (!(requestAttributes instanceof ServletRequestAttributes)) {
//            return null;
//        }
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//        return servletRequestAttributes.getRequest();
//    }

    /**
     * 获取 HttpServletRequest
     * @return {HttpServletRequest}
     */
    public static Optional<HttpServletRequest> getRequest() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        if (servletRequestAttributes == null) {
            return Optional.empty();
        }
        return Optional.of(servletRequestAttributes.getRequest());
    }

    /**
     * 从request 获取CLIENT_ID
     * @return
     */
    @SneakyThrows
    public static String getClientId(ServerHttpRequest request) {
        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return splitClient(header)[0];
    }

    @NotNull
    private static String[] splitClient(String header) {
        if (header == null || !header.startsWith(BASIC_)) {
            throw new CheckedException("请求头中client信息为空");
        }
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new CheckedException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new CheckedException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

}
