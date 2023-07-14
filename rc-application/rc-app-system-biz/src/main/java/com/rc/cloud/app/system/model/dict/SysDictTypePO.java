package com.rc.cloud.app.system.model.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典类型表
 */
@TableName("sys_dict_type")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDictTypePO extends BaseDO {

    /**
     * 字典主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 状态
     * <p>
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
