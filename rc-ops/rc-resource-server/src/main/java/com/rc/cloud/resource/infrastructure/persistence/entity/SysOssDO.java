package com.rc.cloud.resource.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.util.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OSS对象存储对象
 * @author hqf@rc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oss")
public class SysOssDO extends BaseDO {

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

}
