package com.rc.cloud.resource.domain.model.oss;

import com.rc.cloud.common.core.domain.Entity;

/**
 * Oss文件
 *
 * @author hqf@rc
 * @date 2022-04-22
 **/
public class Oss implements Entity<Oss> {

    private OssId ossId;

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 原名
     */
    private String originalName;
    /**
     * 文件后缀名
     */
    private String fileSuffix;
    /**
     * URL地址
     */
    private String url;
    /**
     * 服务商
     */
    private String service;

    private String delFlag;


    public Oss(OssId ossId, String fileName, String originalName, String fileSuffix, String url, String service) {
        this.ossId = ossId;
        this.fileName = fileName;
        this.originalName = originalName;
        this.fileSuffix = fileSuffix;
        this.url = url;
        this.service = service;
    }

    /**
     * 删除
     */
    public void delete() {
        this.delFlag = "1";
    }

    @Override
    public boolean sameIdentityAs(Oss other) {
        return other != null && ossId.sameValueAs(other.ossId);
    }

    public OssId getOssId() {
        return ossId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getUrl() {
        return url;
    }

    public String getService() {
        return service;
    }
}
