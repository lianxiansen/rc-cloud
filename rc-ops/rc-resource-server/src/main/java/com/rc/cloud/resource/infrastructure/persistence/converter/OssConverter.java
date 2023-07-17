package com.rc.cloud.resource.infrastructure.persistence.converter;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.resource.application.command.OssCommand;
import com.rc.cloud.resource.domain.model.oss.Oss;
import com.rc.cloud.resource.infrastructure.persistence.entity.SysOssDO;

/**
 * Oss转换类
 *
 * @author hqf@rc
 * @date 2022-04-23 09:58
 **/
public class OssConverter {


    public static SysOssDO fromOss(Oss oss) {
        SysOssDO sysOssDO = new SysOssDO();
        BeanUtil.copyProperties(oss, sysOssDO);
        sysOssDO.setId(oss.getId());
        return sysOssDO;
    }

    public static Oss toOss(SysOssDO sysOssDO) {
        if (sysOssDO == null) {
            return null;
        }
        Oss oss = new Oss(sysOssDO.getId(), sysOssDO.getFileName(),
                sysOssDO.getOriginalName(), sysOssDO.getFileSuffix(), sysOssDO.getUrl(), sysOssDO.getService());
        return oss;
    }

    public static Oss toOss(final OssCommand ossCommand) {
        return new Oss(ossCommand.getId(), ossCommand.getFileName(), ossCommand.getOriginalName(),
                ossCommand.getFileSuffix(), ossCommand.getUrl(), ossCommand.getService());
    }
}
