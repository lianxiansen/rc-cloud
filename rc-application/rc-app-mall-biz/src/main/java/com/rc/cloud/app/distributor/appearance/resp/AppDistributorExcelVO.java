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
    private Integer id;

    @ExcelProperty("企业名称")
    private String companyName;

    @ExcelProperty("联系人")
    private String contacts;

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
    private String start;

    @ExcelProperty("合作结束")
    private String end;

    @ExcelProperty("对接状态1未对接2已对接")
    private Integer status;

    @ExcelProperty("备注")
    private String remarks;

    @ExcelProperty(value = "创建时间",converter = EasyExcelLocalDateTimeConvert.class)
    private LocalDateTime createTime;

    @ExcelProperty("数据添加人员")
    private String creator;

    @ExcelProperty("管理员id")
    private Integer adminId;

    @ExcelProperty("联系电话")
    private String telephone;

    @ExcelProperty("客户渠道id")
    private Integer channel;

    @ExcelProperty("获客方式id")
    private Integer source;

    @ExcelProperty("客户等级id")
    private Integer level;

    @ExcelProperty("信誉等级")
    private Integer reputation;

    @ExcelProperty("成立时间")
    private String establishedTime;

    @ExcelProperty("展厅地址")
    private String ztAddress;

    @ExcelProperty("展厅面积")
    private String ztMianji;

    @ExcelProperty("展厅图片")
    private String ztImage;

    @ExcelProperty("门店地址")
    private String mdAddress;

    @ExcelProperty("门店面积")
    private String mdMianji;

    @ExcelProperty("门店图片")
    private String mdImage;

    @ExcelProperty("仓库地址")
    private String ckAddress;

    @ExcelProperty("仓库面积")
    private String ckMianji;

    @ExcelProperty("仓库图片")
    private String ckImage;

    @ExcelProperty("是否锁定")
    private Integer locking;

}
