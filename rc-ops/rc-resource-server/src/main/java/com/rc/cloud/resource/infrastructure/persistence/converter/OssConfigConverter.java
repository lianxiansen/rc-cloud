package com.rc.cloud.resource.infrastructure.persistence.converter;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.resource.domain.model.oss.Oss;
import com.rc.cloud.resource.domain.model.oss.OssId;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfig;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfigId;
import com.rc.cloud.resource.infrastructure.persistence.entity.SysOssConfigDO;
import com.rc.cloud.resource.infrastructure.persistence.entity.SysOssDO;

/**
 * Oss转换类
 *
 * @author hqf@rc
 * @date 2022-04-23 10:06
 **/
public class OssConfigConverter {

    public static SysOssConfigDO fromOssConfig(OssConfig ossConfig) {
        SysOssConfigDO sysOssConfigDO = new SysOssConfigDO();
        BeanUtil.copyProperties(ossConfig, sysOssConfigDO);
        sysOssConfigDO.setId(ossConfig.getOssConfigId().getId());
        return sysOssConfigDO;
    }

    public static OssConfig toOssConfig(SysOssConfigDO sysOssConfigDO) {
        if (sysOssConfigDO == null) {
            return null;
        }
        OssConfig ossConfig = new OssConfig(new OssConfigId(sysOssConfigDO.getId()), sysOssConfigDO.getConfigKey(),
                sysOssConfigDO.getAccessKey(), sysOssConfigDO.getSecretKey(), sysOssConfigDO.getBucketName(), sysOssConfigDO.getPrefix(),
                sysOssConfigDO.getEndpoint(), sysOssConfigDO.getIsHttps(), sysOssConfigDO.getRegion(), sysOssConfigDO.getStatus(),
                sysOssConfigDO.getExt1(), sysOssConfigDO.getRemark());
        return ossConfig;
    }
}
