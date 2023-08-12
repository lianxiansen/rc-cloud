package com.rc.cloud.resource.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 对象存储配置视图对象 sys_oss_config
 * @author hqf@rc
 * @date 2022-04-21
 */
@Data
@ApiModel("对象存储配置视图对象")
public class OssConfigDTO {

    private static final long serialVersionUID = 1L;

    public OssConfigDTO() {}

    public OssConfigDTO(String id, String configKey, String accessKey, String secretKey, String bucketName, String prefix, String endpoint, String isHttps, String region, String status, String ext1, String remark) {
        this.id = id;
        this.configKey = configKey;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.prefix = prefix;
        this.endpoint = endpoint;
        this.isHttps = isHttps;
        this.region = region;
        this.status = status;
        this.ext1 = ext1;
        this.remark = remark;
    }

    /**
     * 主建
     */
    @ApiModelProperty("主建")
    private String id;

    /**
     * 配置key
     */
    @ApiModelProperty("配置key")
    private String configKey;

    /**
     * accessKey
     */
    @ApiModelProperty("accessKey")
    private String accessKey;

    /**
     * 秘钥
     */
    @ApiModelProperty("secretKey")
    private String secretKey;

    /**
     * 桶名称
     */
    @ApiModelProperty("桶名称")
    private String bucketName;

    /**
     * 前缀
     */
    @ApiModelProperty("前缀")
    private String prefix;

    /**
     * 访问站点
     */
    @ApiModelProperty("访问站点")
    private String endpoint;

    /**
     * 是否https（Y=是,N=否）
     */
    @ApiModelProperty("是否https（Y=是,N=否）")
    private String isHttps;

    /**
     * 域
     */
    @ApiModelProperty("域")
    private String region;

    /**
     * 状态（0=正常,1=停用）
     */
    @ApiModelProperty("状态（0=正常,1=停用）")
    private String status;

    /**
     * 扩展字段
     */
    @ApiModelProperty("扩展字段")
    private String ext1;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
