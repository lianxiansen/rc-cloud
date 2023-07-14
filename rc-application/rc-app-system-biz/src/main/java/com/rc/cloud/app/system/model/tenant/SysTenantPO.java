package com.rc.cloud.app.system.model.tenant;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户表
 */
@TableName(value = "sys_tenant", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysTenantPO extends BaseDO {

    /**
     * 套餐编号 - 系统
     */
    public static final String PACKAGE_ID_SYSTEM = "0";

    /**
     * 租户编号，自增
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 租户名，唯一
     */
    private String name;
    /**
     * 联系人的用户编号
     * <p>
     * 关联 SysUserVO#getId()
     */
    private String contactUserId;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系手机
     */
    private String contactMobile;

    /**
     * 租户状态
     * <p>
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 绑定域名
     * <p>
     * TODO 芋艿：目前是预留字段，未来会支持根据域名，自动查询到对应的租户。等等
     */
    private String domain;

    /**
     * 租户套餐编号
     * <p>
     * //     * 关联  SysTenantPackagePO#getId()
     * 特殊逻辑：系统内置租户，不使用套餐，暂时使用 {@link #PACKAGE_ID_SYSTEM} 标识
     */
    private String packageId;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 账号数量
     */
    private Integer accountCount;
}
