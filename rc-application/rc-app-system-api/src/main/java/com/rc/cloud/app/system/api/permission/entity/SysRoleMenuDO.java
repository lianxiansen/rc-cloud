package com.rc.cloud.app.system.api.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联
 *
 * @author ruoyi
 */
@TableName("sys_role_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenuDO extends TenantBaseDO {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 菜单ID
     */
    private String menuId;

}
