package com.rc.cloud.app.system.common.datapermission.core.annotation;


import com.rc.cloud.app.system.common.datapermission.core.rule.DataPermissionRule;

import java.lang.annotation.*;

/**
 * @author: 芋道源码
 * @version: 2021年07月04日 17:22:00
 * @description: 数据权限注解,可声明在类或者方法上，标识使用的数据权限规则
 * @date: 2020/2/2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /** @
     * 当前类或方法是否开启数据权限
     * 即使不添加 @DataPermission 注解，默认是开启状态
     * 可通过设置 enable 为 false 禁用
     * @return 是否开启数据权限
     */
    boolean enable() default true;

    /** @
     * 生效的数据权限规则数组，优先级高于 {@link #excludeRules()}
     * @return 生效的数据权限规则数组
     */
    Class<? extends DataPermissionRule>[] includeRules() default {};

    /** @
     * 排除的数据权限规则数组，优先级最低
     * @return 排除的数据权限规则数组
     */
    Class<? extends DataPermissionRule>[] excludeRules() default {};

}
