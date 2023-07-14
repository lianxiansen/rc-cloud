package com.rc.cloud.app.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色类型枚举类
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    /**
     * 内置角色
     */
    SYSTEM(1),

    /**
     * 自定义角色
     */
    CUSTOM(2);

    /**
     * 类型
     */
    private final Integer type;
}
