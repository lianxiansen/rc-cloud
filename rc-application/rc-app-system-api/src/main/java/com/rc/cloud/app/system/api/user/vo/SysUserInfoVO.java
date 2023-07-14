package com.rc.cloud.app.system.api.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rc.cloud.common.mybatis.core.type.JsonStringSetTypeHandler;
import lombok.Data;

import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/06
 * @description Admin 用户 VO
 */
@Data
public class SysUserInfoVO {

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
     * 用户昵称
     */
    private String nickname;

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
     * 枚举 CommonStatusEnum
     */
    private Integer status;
}
