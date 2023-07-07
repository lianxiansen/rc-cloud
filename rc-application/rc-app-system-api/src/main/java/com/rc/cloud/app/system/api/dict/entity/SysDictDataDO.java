package com.rc.cloud.app.system.api.dict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表
 *
 * @author ruoyi
 */
@TableName("sys_dict_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictDataDO extends BaseDO {

    /**
     * 字典数据编号
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private String value;
    /**
     * 字典类型
     *
     * 冗余 {@link SysDictDataDO#getDictType()}
     */
    private String dictType;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 颜色类型
     *
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
