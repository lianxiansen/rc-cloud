package com.rc.cloud.app.system.model.oauth2;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.core.enums.UserTypeEnum;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * OAuth2 批准 DO
 *
 * 用户在 sso.vue 界面时，记录接受的 scope 列表
 *
 * @author 芋道源码
 */
@TableName(value = "sys_oauth2_approve", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2ApproveDO extends BaseDO {

    /**
     * 编号，数据库自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 客户端编号
     *
     * 关联 {@link OAuth2ClientDO#getId()}
     */
    private String clientId;
    /**
     * 授权范围
     */
    private String scope;
    /**
     * 是否接受
     *
     * true - 接受
     * false - 拒绝
     */
    private Boolean approved;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
