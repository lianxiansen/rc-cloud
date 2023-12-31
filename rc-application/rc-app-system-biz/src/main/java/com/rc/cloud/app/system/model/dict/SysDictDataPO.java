package com.rc.cloud.app.system.model.dict;

import com.baomidou.mybatisplus.annotation.*;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典数据表
 */
@TableName("sys_dict_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictDataPO extends BaseDO {

    /**
     * 字典数据编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典排序
     */
    private Integer sort;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    @TableField(value = "`value`")
    private String value;

    /**
     * 字典类型
     * <p>
     * 冗余 {@link SysDictDataPO#getDictType()}
     */
    private String dictType;

    /**
     * 状态
     * <p>
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 颜色类型
     * <p>
     * 对应到 element-ui 为 default、primary、success、info、warning、danger
     */
    private String colorType;

    /**
     * css 样式
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String cssClass;

    /**
     * 备注
     */
    private String remark;

}
