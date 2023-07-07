package com.rc.cloud.app.system.model.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.enums.SexEnum;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import com.rc.cloud.common.mybatis.core.type.JsonStringSetTypeHandler;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 管理后台的用户 DO
 *
 * @author 芋道源码
 */
@TableName(value = "sys_user", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDO extends TenantBaseDO {

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
     *
     * 因为目前使用 {@link BCryptPasswordEncoder} 加密器，所以无需自己处理 salt 盐
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
     *
     * 枚举类 {@link SexEnum}
     */
    private Integer sex;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 帐号状态
     *
     * 枚举 {@link CommonStatusEnum}
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
    @TableField(exist=false)
    private Set<String> authoritySet;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authoritySet == null) {
            return null;
        }
        return authoritySet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public void setAuthorities(Collection<?> authorities) {
        if (authorities == null) {
            return;
        }
        this.authoritySet = authorities.stream().map(Object::toString).collect(Collectors.toSet());
    }
}
