package com.rc.cloud.app.system.model.tenant;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.type.JsonStringSetTypeHandler;
import lombok.*;

import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户套餐信息表
 */
@TableName(value = "sys_tenant_package", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysTenantPackagePO extends BaseDO {

    /**
     * 套餐编号，自增
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 套餐名，唯一
     */
    private String name;

    /**
     * 租户套餐状态
     * <p>
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联的菜单编号
     */
    @TableField(typeHandler = JsonStringSetTypeHandler.class)
    private Set<String> menuIds;

}
