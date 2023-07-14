package com.rc.cloud.app.system.api.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import com.rc.cloud.common.mybatis.core.type.JsonStringSetTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/06
 * @description 管理后台的用户 DO
 */
@TableName(value = "sys_user", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVO extends TenantBaseDO {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 加密后的密码
     * <p>
     * 因为目前使用 BCryptPasswordEncoder 加密器，所以无需自己处理 salt 盐
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 岗位编号数组
     */
    @TableField(typeHandler = JsonStringSetTypeHandler.class)
    private Set<String> postIds;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别
     * <p>
     * 枚举类 SexEnum
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 帐号状态
     * <p>
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 权限列表
     */
    @TableField(exist = false)
    private Set<String> authoritySet;

}

