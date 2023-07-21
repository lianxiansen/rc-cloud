package com.rc.cloud.app.system.model.logger;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author rc@hqf
 * @date 2023/07/19
 * @description 登录日志表
 * 注意，包括登录和登出两种行为
 */
@TableName("sys_login_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginLogPO extends TenantBaseDO {

    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 日志类型
     * <p>
     * 枚举 LoginLogTypeEnum
     */
    private Integer logType;
    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 用户类型
     * <p>
     * 枚举 UserTypeEnum
     */
    private Integer userType;
    /**
     * 用户账号
     * <p>
     * 冗余，因为账号可以变更
     */
    private String username;
    /**
     * 登录结果
     * <p>
     * 枚举 LoginResultEnum
     */
    private Integer result;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

}
