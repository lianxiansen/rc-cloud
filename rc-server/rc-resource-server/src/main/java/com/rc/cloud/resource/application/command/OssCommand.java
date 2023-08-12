package com.rc.cloud.resource.application.command;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * OSS对象存储分页查询对象 sys_oss
 * @author hqf@rc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("OSS对象存储分页查询对象")
public class OssCommand extends BaseDO {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

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
     * 服务商
     */
    @ApiModelProperty("服务商")
    private String service;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
