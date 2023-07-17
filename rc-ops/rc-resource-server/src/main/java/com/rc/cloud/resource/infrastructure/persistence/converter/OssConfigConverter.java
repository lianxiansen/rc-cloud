package com.rc.cloud.resource.infrastructure.persistence.converter;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfig;
import com.rc.cloud.resource.infrastructure.persistence.bo.SysOssConfigBO;

/**
 * Oss转换类
 *
 * @author hqf@rc
 * @date 2022-04-23 10:06
 **/
public class OssConfigConverter {

    public static SysOssConfigBO fromOssConfig(OssConfig ossConfig) {
        SysOssConfigBO sysOssConfigBO = new SysOssConfigBO();
        BeanUtil.copyProperties(ossConfig, sysOssConfigBO);
        return sysOssConfigBO;
    }

    public static OssConfig toOssConfig(SysOssConfigBO sysOssConfigBO) {
        if (sysOssConfigBO == null) {
            return null;
        }
        OssConfig ossConfig = new OssConfig(sysOssConfigBO.getId(), sysOssConfigBO.getConfigKey(),
                sysOssConfigBO.getAccessKey(), sysOssConfigBO.getSecretKey(), sysOssConfigBO.getBucketName(), sysOssConfigBO.getPrefix(),
                sysOssConfigBO.getEndpoint(), sysOssConfigBO.getIsHttps(), sysOssConfigBO.getRegion(), sysOssConfigBO.getStatus(),
                sysOssConfigBO.getExt1(), sysOssConfigBO.getRemark());
        return ossConfig;
    }
}
