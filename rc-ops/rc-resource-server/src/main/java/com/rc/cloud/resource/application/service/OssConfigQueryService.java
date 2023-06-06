package com.rc.cloud.resource.application.service;

import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.resource.application.command.OssConfigCommand;
import com.rc.cloud.resource.application.dto.OssConfigDTO;

/**
 * 对象存储配置Service接口
 * @author hqf@rc
 * @date 2022-04-21
 */
public interface OssConfigQueryService {

    /**
     * 查询单个
     */
    OssConfigDTO queryById(String ossConfigId);

    /**
     * 查询列表
     */
    TableDataInfo<OssConfigDTO> queryPageList(OssConfigCommand cmd, PageQuery pageQuery);
}
