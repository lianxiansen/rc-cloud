package com.rc.cloud.app.system.model.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 岗位表
 */
@TableName("sys_post")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPostPO extends BaseDO {

    /**
     * 岗位序号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 岗位编码
     */
    private String code;

    /**
     * 岗位排序
     */
    private Integer sort;

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
