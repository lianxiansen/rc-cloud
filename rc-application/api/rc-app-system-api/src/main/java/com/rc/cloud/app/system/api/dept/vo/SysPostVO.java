package com.rc.cloud.app.system.api.dept.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;

/**
 * @author hqf
 * @date 2023/07/13
 * @description 岗位表
 */
@Data
public class SysPostVO extends BaseDO {

    /**
     * 岗位序号
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
