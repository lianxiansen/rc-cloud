package com.rc.cloud.resource.infrastructure.persistence.mapper;

import com.rc.cloud.common.mybatis.core.mapper.BaseMapperPlus;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import com.rc.cloud.resource.infrastructure.persistence.bo.SysOssConfigBO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对象存储配置Mapper接口
 * @author hqf@rc
 * @date 2022-04-21
 */
@Mapper
public interface SysOssConfigMapper extends BaseMapperPlus<SysOssConfigMapper, SysOssConfigBO, OssConfigDTO> {
}
