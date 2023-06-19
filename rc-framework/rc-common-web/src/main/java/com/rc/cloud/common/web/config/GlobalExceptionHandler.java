package com.rc.cloud.common.web.config;

import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.core.web.util.WebFrameworkUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;

import static com.rc.cloud.common.core.exception.enums.GlobalErrorCodeConstants.*;


/**
 * 自定义异常处理器
 *
 * @author oliveoil
 * @date 2023-06-02
 **/
/**
 * 全局异常处理器，将 Exception 翻译成 CodeResult + 对应的异常编号
 *
 * @author 芋道源码
 */
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

//    private final String applicationName;

//    private final ApiErrorLogFrameworkService apiErrorLogFrameworkService;

    /**
     * 处理所有异常，主要是提供给 Filter 使用
     * 因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     *
     * @param request 请求
     * @param ex 异常
     * @return 通用返回
     */
    public CodeResult<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }
        if (ex instanceof ValidationException) {
            return validationException((ValidationException) ex);
        }
        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler(request, (NoHandlerFoundException) ex);
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        // 熔断异常，暂时不做处理
//        if (ex instanceof RequestNotPermitted) {
//            return requestNotPermittedExceptionHandler(request, (RequestNotPermitted) ex);
//        }
        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CodeResult<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CodeResult.fail(PARAMETER_ERROR.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CodeResult<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CodeResult.fail(PARAMETER_ERROR.getCode(), String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CodeResult<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CodeResult.fail(PARAMETER_ERROR.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public CodeResult<?> bindExceptionHandler(BindException ex) {
        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CodeResult.fail(PARAMETER_ERROR.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CodeResult<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return CodeResult.fail(PARAMETER_ERROR.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    /**
     * 处理 Dubbo Consumer 本地参数校验时，抛出的 ValidationException 异常
     */
    @ExceptionHandler(value = ValidationException.class)
    public CodeResult<?> validationException(ValidationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        // 无法拼接明细的错误信息，因为 Dubbo Consumer 抛出 ValidationException 异常时，是直接的字符串信息，且人类不可读
        return CodeResult.fail(PARAMETER_ERROR.getCode(), PARAMETER_ERROR.getMsg());
    }

    /**
     * 处理 SpringMVC 请求地址不存在
     *
     * 注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public CodeResult<?> noHandlerFoundExceptionHandler(HttpServletRequest req, NoHandlerFoundException ex) {
        log.warn("[noHandlerFoundExceptionHandler]", ex);
        return CodeResult.fail(NOT_FOUND.getCode(), String.format("请求地址不存在:%s", ex.getRequestURL()));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     *
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CodeResult<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return CodeResult.fail(METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
    }

//    /**
//     * 处理 Resilience4j 限流抛出的异常
//     */
//    @ExceptionHandler(value = RequestNotPermitted.class)
//    public CodeResult<?> requestNotPermittedExceptionHandler(HttpServletRequest req, RequestNotPermitted ex) {
//        log.warn("[requestNotPermittedExceptionHandler][url({}) 访问过于频繁]", req.getRequestURL(), ex);
//        return CodeResult.fail(TOO_MANY_REQUESTS);
//    }

    /**
     * 处理 Spring Security 权限不足的异常
     *
     * 来源是，使用 @PreAuthorize 注解，AOP 进行权限拦截
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CodeResult<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
        log.warn("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})]", WebFrameworkUtils.getLoginUserId(req),
                req.getRequestURL(), ex);
        return CodeResult.fail(FORBIDDEN);
    }

    /**
     * 处理业务异常 ServiceException
     *
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public CodeResult<?> serviceExceptionHandler(ServiceException ex) {
        log.info("[serviceExceptionHandler]", ex);
        return CodeResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public CodeResult<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        // 插入异常日志
//        this.createExceptionLog(req, ex);
        log.error("[defaultExceptionHandler][req({}) 服务器异常]", req.getRequestURL(), ex);
        // 返回 ERROR CommonResult
        return CodeResult.fail(UNKNOWN.getCode(), ex.getMessage()==null? UNKNOWN.getMsg():ex.getMessage());
    }

//    private void createExceptionLog(HttpServletRequest req, Throwable e) {
//        // 插入错误日志
//        ApiErrorLog errorLog = new ApiErrorLog();
//        try {
//            // 初始化 errorLog
//            initExceptionLog(errorLog, req, e);
//            // 执行插入 errorLog
//            apiErrorLogFrameworkService.createApiErrorLog(errorLog);
//        } catch (Throwable th) {
//            log.error("[createExceptionLog][url({}) log({}) 发生异常]", req.getRequestURI(),  JsonUtils.toJsonString(errorLog), th);
//        }
//    }

//    private void initExceptionLog(ApiErrorLog errorLog, HttpServletRequest request, Throwable e) {
//        // 处理用户信息
//        errorLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
//        errorLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
//        // 设置异常字段
//        errorLog.setExceptionName(e.getClass().getName());
//        errorLog.setExceptionMessage(ExceptionUtil.getMessage(e));
//        errorLog.setExceptionRootCauseMessage(ExceptionUtil.getRootCauseMessage(e));
//        errorLog.setExceptionStackTrace(ExceptionUtils.getStackTrace(e));
//        StackTraceElement[] stackTraceElements = e.getStackTrace();
//        Assert.notEmpty(stackTraceElements, "异常 stackTraceElements 不能为空");
//        StackTraceElement stackTraceElement = stackTraceElements[0];
//        errorLog.setExceptionClassName(stackTraceElement.getClassName());
//        errorLog.setExceptionFileName(stackTraceElement.getFileName());
//        errorLog.setExceptionMethodName(stackTraceElement.getMethodName());
//        errorLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
//        // 设置其它字段
//        errorLog.setTraceId(TracerUtils.getTraceId());
//        errorLog.setApplicationName(applicationName);
//        errorLog.setRequestUrl(request.getRequestURI());
//        Map<String, Object> requestParams = MapUtil.<String, Object>builder()
//                .put("query", ServletUtils.getParamMap(request))
//                .put("body", ServletUtils.getBody(request)).build();
//        errorLog.setRequestParams(JsonUtils.toJsonString(requestParams));
//        errorLog.setRequestMethod(request.getMethod());
//        errorLog.setUserAgent(ServletUtils.getUserAgent(request));
//        errorLog.setUserIp(ServletUtils.getClientIP(request));
//        errorLog.setExceptionTime(LocalDateTime.now());
//    }
}
