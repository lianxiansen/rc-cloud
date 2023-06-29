package com.rc.cloud.app.distributor.appearance.resp;

import com.rc.cloud.common.excel.convert.EasyExcelLocalDateTimeConvert;
import lombok.*;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 经销商 Excel VO
 *
 * @author wjf
 */
@Data
public class AppDistributorExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("企业名称")
    private String companyName;

    @ExcelProperty("联系人")
    private String contact;

    @ExcelProperty("联系手机")
    private String mobile;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("省")
    private String province;

    @ExcelProperty("市")
    private String city;

    @ExcelProperty("区")
    private String county;

    @ExcelProperty("详细地址")
    private String address;

    @ExcelProperty("合作开始")
    private String startTime;

    @ExcelProperty("合作结束")
    private String endTime;

    @ExcelProperty("对接状态1未对接2已对接")
    private Integer status;

    @ExcelProperty("备注")
    private String remarks;

    @ExcelProperty(value = "创建时间",converter = EasyExcelLocalDateTimeConvert.class)
    private LocalDateTime createTime;

    @ExcelProperty("数据添加人员")
    private String creator;

    @ExcelProperty("管理员id")
    private Long adminId;

    @ExcelProperty("联系电话")
    private String telephone;

    @ExcelProperty("客户渠道id")
    private Long channel;

    @ExcelProperty("获客方式id")
    private Long source;

    @ExcelProperty("客户等级id")
    private Long level;

    @ExcelProperty("信誉等级")
    private Long reputation;

    @ExcelProperty("成立时间")
    private String establishedTime;

    @ExcelProperty("是否锁定")
    private Integer locking;

}
