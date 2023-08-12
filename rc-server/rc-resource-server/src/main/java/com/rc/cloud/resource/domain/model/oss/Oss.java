package com.rc.cloud.resource.domain.model.oss;

import com.rc.cloud.common.core.domain.Entity;
import lombok.Data;

/**
 * Oss文件
 *
 * @author hqf@rc
 * @date 2022-04-22
 **/
@Data
public class Oss {

    private String id;

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

    private String deleted;


    public Oss(String id, String fileName, String originalName, String fileSuffix, String url, String service) {
        this.id = id;
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
        this.deleted = "1";
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
