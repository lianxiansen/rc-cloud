package com.rc.cloud.app.system.model.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import com.rc.cloud.common.mybatis.core.type.JsonStringSetTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色表
 */
@TableName(value = "sys_role", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePO extends TenantBaseDO {

    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * <p>
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 角色类型
     * <p>
     * 枚举 RoleTypeEnum
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;


    /**
     * 数据范围
     * <p>
     * 枚举 DataScopeEnum
     */
    private Integer dataScope;

    /**
     * 数据范围(指定部门数组)
     * <p>
     * 适用于 {@link #dataScope} 的值为 DataScopeEnum#DEPT_CUSTOM 时
     */
    @TableField(typeHandler = JsonStringSetTypeHandler.class)
    private Set<String> dataScopeDeptIds;

}
