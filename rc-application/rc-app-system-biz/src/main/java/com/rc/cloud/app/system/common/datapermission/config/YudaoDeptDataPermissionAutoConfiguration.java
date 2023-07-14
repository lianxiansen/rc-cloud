package com.rc.cloud.app.system.common.datapermission.config;

import com.rc.cloud.app.system.common.datapermission.core.rule.dept.DeptDataPermissionRule;
import com.rc.cloud.app.system.common.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 基于部门的数据权限 AutoConfiguration
 *
 * @author 芋道源码
 */
@Configuration
//@ConditionalOnClass(LoginUser.class)
@ConditionalOnBean(value = {DeptDataPermissionRuleCustomizer.class})
public class YudaoDeptDataPermissionAutoConfiguration {

    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(List<DeptDataPermissionRuleCustomizer> customizers) {
        // 创建 DeptDataPermissionRule 对象
        DeptDataPermissionRule rule = new DeptDataPermissionRule();
        // 补全表配置
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }

}
