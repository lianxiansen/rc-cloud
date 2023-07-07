package com.rc.cloud.app.system.vo.dict.type;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rc.cloud.app.system.enums.DictTypeConstants;
import com.rc.cloud.common.excel.annotations.DictFormat;
import com.rc.cloud.common.excel.convert.DictConvert;
import lombok.Data;

/**
 * 字典类型 Excel 导出响应 VO
 */
@Data
public class DictTypeExcelVO {

    @ExcelProperty("字典主键")
    private String id;

    @ExcelProperty("字典名称")
    private String name;

    @ExcelProperty("字典类型")
    private String type;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

}
