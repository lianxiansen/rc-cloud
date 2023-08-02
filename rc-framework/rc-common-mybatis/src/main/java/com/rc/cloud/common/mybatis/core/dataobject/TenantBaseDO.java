package com.rc.cloud.common.mybatis.core.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 拓展多租户的 BaseDO 基类
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TenantBaseDO extends BaseDO {

    /**
     * 多租户编号
     */
    @TableField("tenant_id")
    private String tenantId;

}
