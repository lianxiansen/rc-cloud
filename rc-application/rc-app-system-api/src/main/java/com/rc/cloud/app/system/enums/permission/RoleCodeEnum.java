package com.rc.cloud.app.system.enums.permission;

import com.rc.cloud.common.core.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    /**
     * 超级管理员
     */
    SUPER_ADMIN("super_admin", "超级管理员"),

    /**
     * 租户管理员
     */
    TENANT_ADMIN("tenant_admin", "租户管理员");

    /**
     * 角色编码
     */
    private final String code;

    /**
     * 名字
     */
    private final String name;

    /**
     * 判断是否是超级管理员
     *
     * @param code 角色编码
     * @return 是否是超级管理员
     */
    public static boolean isSuperAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
    }
}
