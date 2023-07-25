package com.rc.cloud.app.system.api.permission.vo;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description 系统角色 DO
 */
@Data
public class SysRoleVO extends BaseDO {

    /**
     * 角色ID
     */
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
     * <p>
     * 枚举
     */
    private String code;

    /**
     * 角色排序
     */
    private Integer sort;

    /**
     * 角色状态
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 角色类型
     * 枚举 RoleTypeEnum
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据范围
     * 枚举 DataScopeEnum
     */
    private Integer dataScope;

    /**
     * 数据范围(指定部门数组)
     * 适用于 #dataScope 的值为 DataScopeEnum#DEPT_CUSTOM 时
     */
    private Set<String> dataScopeDeptIds;

    /**
     * 租户ID
     */
    private String tenantId;
}
