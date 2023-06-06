package com.rc.cloud.resource.application.service;

import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 文件上传 服务层
 * @author hqf@rc
 */
public interface OssApplicationService {

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
