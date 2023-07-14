package com.rc.cloud.app.system.model.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 用户角色关联表
 */
@TableName("sys_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRolePO extends BaseDO {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色 ID
     */
    private String roleId;

}
