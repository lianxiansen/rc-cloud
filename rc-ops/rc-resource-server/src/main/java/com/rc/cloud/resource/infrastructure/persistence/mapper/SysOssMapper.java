package com.rc.cloud.resource.infrastructure.persistence.mapper;

import com.rc.cloud.common.mybatis.core.mapper.BaseMapperPlus;
import com.rc.cloud.resource.application.dto.OssDTO;
import com.rc.cloud.resource.infrastructure.persistence.entity.SysOssDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传 数据层
 * @author hqf@rc
 */
@Mapper
public interface SysOssMapper extends BaseMapperPlus<SysOssMapper, SysOssDO, OssDTO> {


}
