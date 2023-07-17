package com.rc.cloud.resource.domain.model.ossConfig;

import com.rc.cloud.common.core.domain.AbstractId;
import lombok.Data;

/**
 * Oss文件
 *
 * @author hqf@rc
 * @date 2022-04-22
 **/
@Data
public class OssConfig {

    private String id;

    /**
     * 配置key
     */
    private String configKey;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 访问站点
     */
    private String endpoint;

    /**
     * 是否https（0否 1是）
     */
    private String isHttps;

    /**
     * 域
     */
    private String region;

    /**
     * 状态(0正常 1停用)
     */
    private String status;

    /**
     * 扩展字段
     */
    private String ext1;

    /**
     * 备注
     */
    private String remark;

    private String delFlag;


    public OssConfig(String id, String configKey, String accessKey, String secretKey, String bucketName, String prefix, String endpoint, String isHttps, String region, String status, String ext1, String remark) {
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
     * 删除
     */
    public void delete() {
        this.delFlag = "1";
    }

    /**
     * 禁用
     */
    public void disable() {
        this.status = "1";
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getIsHttps() {
        return isHttps;
    }

    public String getRegion() {
        return region;
    }

    public String getStatus() {
        return status;
    }

    public String getExt1() {
        return ext1;
    }

    public String getRemark() {
        return remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

}
