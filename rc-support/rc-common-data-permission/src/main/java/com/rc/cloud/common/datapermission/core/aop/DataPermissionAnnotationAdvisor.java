package com.rc.cloud.common.datapermission.core.aop;

import com.rc.cloud.common.datapermission.core.annotation.DataPermission;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description DataPermission 注解的 Advisor 实现类
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class DataPermissionAnnotationAdvisor extends AbstractPointcutAdvisor {

    private final Advice advice;

    private final Pointcut pointcut;

    /**
     * 构造方法
     */
    public DataPermissionAnnotationAdvisor() {
        this.advice = new DataPermissionAnnotationInterceptor();
        this.pointcut = this.buildPointcut();
    }

    /**
     * 构建切点
     * @return 切点
     */
    protected Pointcut buildPointcut() {
        Pointcut classPointcut = new AnnotationMatchingPointcut(DataPermission.class, true);
        Pointcut methodPointcut = new AnnotationMatchingPointcut(null, DataPermission.class, true);
        return new ComposablePointcut(classPointcut).union(methodPointcut);
    }
}
