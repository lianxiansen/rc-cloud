package com.rc.cloud.app.system.vo.dept.post;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rc.cloud.app.system.enums.DictTypeConstants;
import com.rc.cloud.common.excel.annotations.DictFormat;
import com.rc.cloud.common.excel.convert.DictConvert;
import lombok.Data;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:49
 * @description 岗位 Excel 导出响应 VO
 */
@Data
public class PostExcelVO {

    @ExcelProperty("岗位序号")
    private String id;

    @ExcelProperty("岗位编码")
    private String code;

    @ExcelProperty("岗位名称")
    private String name;

    @ExcelProperty("岗位排序")
    private Integer sort;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private String status;
}
