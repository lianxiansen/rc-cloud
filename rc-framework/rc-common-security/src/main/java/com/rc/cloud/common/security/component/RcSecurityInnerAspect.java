package com.rc.cloud.common.security.component;

import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.security.annotation.Inner;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lengleng
 * @date 2022-06-04
 * <p>
 * 服务间接口不鉴权处理逻辑
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class RcSecurityInnerAspect implements Ordered {

    private final HttpServletRequest request;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @SneakyThrows
    @Around("@within(inner) || @annotation(inner)")
    public Object around(ProceedingJoinPoint point, Inner inner) {
        if (inner == null) {
            Class<?> clazz = point.getTarget().getClass();
            inner = AnnotationUtils.findAnnotation(clazz, Inner.class);
        }
        String header = request.getHeader(SecurityConstants.FROM);
        if (inner.value() && !SecurityConstants.FROM_IN.equals(header)) {
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new AccessDeniedException("Access is denied");
        }
        return point.proceed();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

}
