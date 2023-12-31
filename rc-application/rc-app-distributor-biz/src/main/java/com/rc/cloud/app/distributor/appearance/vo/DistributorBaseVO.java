package com.rc.cloud.app.distributor.appearance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 经销商 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DistributorBaseVO {

    @Schema(description = "企业名称", example = "王五")
    private String companyName;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String county;

    @Schema(description = "省编码")
    private String provinceCode;

    @Schema(description = "市编码")
    private String cityCode;

    @Schema(description = "区编码")
    private String countyCode;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "合作开始")
    private LocalDateTime startTime;

    @Schema(description = "合作结束")
    private LocalDateTime endTime;

    @Schema(description = "对接状态1未对接2已对接", example = "1")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "管理员id", example = "8530")
    private String adminId;

    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "客户渠道id")
    private String channel;

    @Schema(description = "获客方式id")
    private String source;

    @Schema(description = "客户等级id")
    private String level;

    @Schema(description = "信誉等级")
    private String reputation;

    @Schema(description = "成立时间")
    private String establishedTime;

    @Schema(description = "是否锁定")
    private Integer locking;

    @Schema(description = "是否处于回收站")
    private Integer recycleFlag;
}
