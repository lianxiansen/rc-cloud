package com.rc.cloud.common.security.core.aop;

import com.rc.cloud.common.security.core.annotations.PreAuthenticated;
import com.rc.cloud.common.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.rc.cloud.common.core.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;


@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw exception(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
