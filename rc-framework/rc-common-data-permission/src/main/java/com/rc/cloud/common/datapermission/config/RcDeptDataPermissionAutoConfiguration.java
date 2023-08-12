package com.rc.cloud.common.datapermission.config;

import com.rc.cloud.app.system.api.permission.PermissionApi;
import com.rc.cloud.common.datapermission.core.rule.dept.DeptDataPermissionRule;
import com.rc.cloud.common.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 基于部门的数据权限 AutoConfiguration
 */
@Configuration
//@ConditionalOnClass(LoginUser.class)
@ConditionalOnBean(value = {DeptDataPermissionRuleCustomizer.class})
public class RcDeptDataPermissionAutoConfiguration {

    /**
     * 创建 DeptDataPermissionRule 对象
     *
     * @param customizers {@link DeptDataPermissionRuleCustomizer} 集合
     * @return {@link DeptDataPermissionRule}
     */
    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(PermissionApi permissionApi, List<DeptDataPermissionRuleCustomizer> customizers) {
        // 创建 DeptDataPermissionRule 对象
        DeptDataPermissionRule rule = new DeptDataPermissionRule(permissionApi);
        // 补全表配置
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }
}
