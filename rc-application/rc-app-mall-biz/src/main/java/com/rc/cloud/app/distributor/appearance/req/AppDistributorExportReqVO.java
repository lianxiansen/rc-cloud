package com.rc.cloud.app.distributor.appearance.req;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import static com.rc.cloud.common.core.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 经销商 Excel 导出 Request VO，参数和 DistributorPageReqVO 是一致的")
@Data
public class AppDistributorExportReqVO {

    @Schema(description = "企业名称", example = "王五")
    private String companyName;

    @Schema(description = "联系人")
    private String contacts;

    @Schema(description = "联系手机")
    private String mobile;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String county;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "合作开始")
    private String start;

    @Schema(description = "合作结束")
    private String end;

    @Schema(description = "对接状态1未对接2已对接", example = "1")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] createTime;

    @Schema(description = "数据添加人员")
    private String creator;

    @Schema(description = "管理员id", example = "8530")
    private Integer adminId;

    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "客户渠道id")
    private Integer channel;

    @Schema(description = "获客方式id")
    private Integer source;

    @Schema(description = "客户等级id")
    private Integer level;

    @Schema(description = "信誉等级")
    private Integer reputation;

    @Schema(description = "成立时间")
    private String establishedTime;

    @Schema(description = "展厅地址")
    private String ztAddress;

    @Schema(description = "展厅面积")
    private String ztMianji;

    @Schema(description = "展厅图片")
    private String ztImage;

    @Schema(description = "门店地址")
    private String mdAddress;

    @Schema(description = "门店面积")
    private String mdMianji;

    @Schema(description = "门店图片")
    private String mdImage;

    @Schema(description = "仓库地址")
    private String ckAddress;

    @Schema(description = "仓库面积")
    private String ckMianji;

    @Schema(description = "仓库图片")
    private String ckImage;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "是否锁定")
    private Integer locking;

}
