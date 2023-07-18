package com.rc.cloud.resource.infrastructure.persistence.converter;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.resource.application.command.OssCommand;
import com.rc.cloud.resource.domain.model.oss.Oss;
import com.rc.cloud.resource.infrastructure.persistence.bo.SysOssBO;

/**
 * Oss转换类
 *
 * @author hqf@rc
 * @date 2022-04-23 09:58
 **/
public class OssConverter {


    public static SysOssBO fromOss(Oss oss) {
        SysOssBO sysOssBO = new SysOssBO();
        BeanUtil.copyProperties(oss, sysOssBO);
        return sysOssBO;
    }

    public static Oss toOss(SysOssBO sysOssBO) {
        if (sysOssBO == null) {
            return null;
        }
        Oss oss = new Oss(sysOssBO.getId(), sysOssBO.getFileName(),
                sysOssBO.getOriginalName(), sysOssBO.getFileSuffix(), sysOssBO.getUrl(), sysOssBO.getService());
        return oss;
    }

    public static Oss toOss(final OssCommand ossCommand) {
        return new Oss(ossCommand.getId(), ossCommand.getFileName(), ossCommand.getOriginalName(),
                ossCommand.getFileSuffix(), ossCommand.getUrl(), ossCommand.getService());
    }
}
