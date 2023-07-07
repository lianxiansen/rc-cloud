package com.rc.cloud.app.system.api.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.enums.SexEnum;
import com.rc.cloud.common.mybatis.core.type.JsonStringSetTypeHandler;
import lombok.Data;

import java.util.Set;

/**
 * Admin 用户 VO
 *
 * @author oliveoil
 * @date 2023/07/06
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
}
