package com.rc.cloud.app.distributor.appearance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 经销商 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AppDistributorBaseVO {

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
    private LocalDateTime createTime;

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
