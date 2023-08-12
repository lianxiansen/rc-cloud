package com.rc.cloud.resource.application.assembler;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.resource.application.command.OssConfigCommand;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfig;

/**
 * Assembler class for the AuthenticationDTOAssembler.
 *
 * @author hqf@rc
 * @date 2022-04-25 09:36
 * 可以写的方法：from，to，getList等
 **/
public class OssConfigDTOAssembler {

    public static OssConfigDTO fromOssConfig(final OssConfig ossConfig) {
        OssConfigDTO dto = new OssConfigDTO();
        BeanUtil.copyProperties(ossConfig, dto);
        dto.setId(ossConfig.getId() == null ? null : ossConfig.getId());
        return dto;
    }

    public static OssConfig toOssConfig(final OssConfigDTO ossConfigDTO) {
        return new OssConfig(ossConfigDTO.getId(), ossConfigDTO.getConfigKey(),
                ossConfigDTO.getAccessKey(), ossConfigDTO.getSecretKey(),
                ossConfigDTO.getBucketName(), ossConfigDTO.getPrefix(),
                ossConfigDTO.getEndpoint(), ossConfigDTO.getIsHttps(),
                ossConfigDTO.getRegion(), ossConfigDTO.getStatus(),
                ossConfigDTO.getExt1(), ossConfigDTO.getRemark());
    }

    public static OssConfig toOssConfig(final OssConfigCommand ossConfigCommand) {
        return new OssConfig(ossConfigCommand.getId(), ossConfigCommand.getConfigKey(),
                ossConfigCommand.getAccessKey(), ossConfigCommand.getSecretKey(),
                ossConfigCommand.getBucketName(), ossConfigCommand.getPrefix(),
                ossConfigCommand.getEndpoint(), ossConfigCommand.getIsHttps(),
                ossConfigCommand.getRegion(), ossConfigCommand.getStatus(),
                ossConfigCommand.getExt1(), ossConfigCommand.getRemark());
    }
}
