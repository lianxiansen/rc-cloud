package com.rc.cloud.common.security.annotation;

import com.rc.cloud.common.security.component.RcResourceServerAutoConfiguration;
import com.rc.cloud.common.security.component.RcResourceServerConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * @author lengleng
 * @date 2022-06-04
 * <p>
 * 资源服务注解
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ RcResourceServerAutoConfiguration.class, RcResourceServerConfiguration.class })
public @interface EnablePigResourceServer {

}
