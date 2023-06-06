package com.rc.cloud.resource.application.service;

import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.common.mybatis.util.Page;
import com.rc.cloud.resource.application.command.OssCommand;
import com.rc.cloud.resource.application.dto.OssDTO;
import com.rc.cloud.resource.domain.model.oss.Oss;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文件上传 服务层
 * @author hqf@rc
 */
public interface OssQueryService {

    /**
     * 分页查询
     */
    TableDataInfo<OssDTO> queryPage(OssCommand sysOss, PageQuery pageQuery);

    Oss getById(String ossId);
}
