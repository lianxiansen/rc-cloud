package com.rc.cloud.resource.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * OSS对象存储视图对象 sys_oss
 * @author hqf@rc
 */
@Data
@ApiModel("OSS对象存储视图对象")
public class OssDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public OssDTO() {}

    public OssDTO(String ossId, String fileName, String originalName, String fileSuffix, String url, Date createTime, String createBy, String service) {
        this.ossId = ossId;
        this.fileName = fileName;
        this.originalName = originalName;
        this.fileSuffix = fileSuffix;
        this.url = url;
        this.createTime = createTime;
        this.createBy = createBy;
        this.service = service;
    }

    /**
     * 对象存储主键
     */
    @ApiModelProperty("对象存储主键")
    private String ossId;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String fileName;

    /**
     * 原名
     */
    @ApiModelProperty("原名")
    private String originalName;

    /**
     * 文件后缀名
     */
    @ApiModelProperty("文件后缀名")
    private String fileSuffix;

    /**
     * URL地址
     */
    @ApiModelProperty("URL地址")
    private String url;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 上传人
     */
    @ApiModelProperty("上传人")
    private String createBy;

    /**
     * 服务商
     */
    @ApiModelProperty("服务商")
    private String service;
}
