package com.rc.cloud.app.system.vo.tenant.tenant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rc.cloud.app.system.enums.DictTypeConstants;
import com.rc.cloud.common.excel.annotations.DictFormat;
import com.rc.cloud.common.excel.convert.DictConvert;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 租户 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class TenantExcelVO {

    @ExcelProperty("租户编号")
    private Long id;

    @ExcelProperty("租户名")
    private String name;

    @ExcelProperty("联系人")
    private String contactName;

    @ExcelProperty("联系手机")
    private String contactMobile;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
