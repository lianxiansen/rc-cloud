package com.rc.cloud.app.system.model.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 部门表
 */
@TableName("sys_dept")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptPO extends TenantBaseDO {

    /**
     * 部门ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 父部门ID
     * <p>
     * 关联 {@link #id}
     */
    private String parentId;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 负责人
     * <p>
     * 关联 SysUserVO#getId()
     */
    private String leaderUserId;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 部门状态
     * <p>
     * 枚举 CommonStatusEnum
     */
    private Integer status;

}
