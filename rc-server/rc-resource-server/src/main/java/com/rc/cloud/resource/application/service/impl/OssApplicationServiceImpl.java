package com.rc.cloud.resource.application.service.impl;

import com.rc.cloud.common.oss.factory.OssFactory;
import com.rc.cloud.common.oss.service.IOssStrategy;
import com.rc.cloud.resource.application.service.OssApplicationService;
import com.rc.cloud.resource.domain.model.oss.Oss;
import com.rc.cloud.resource.domain.model.oss.OssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 文件上传 服务层实现
 *
 * @author hqf@rc
 */
@Service
@Primary
public class OssApplicationServiceImpl implements OssApplicationService {

    @Autowired
    private OssRepository ossRepository;

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // 做一些业务上的校验,判断是否需要校验
        }
        List<Oss> list = ossRepository.selectBatchIds(ids);
        for (Oss oss : list) {
            IOssStrategy storage = OssFactory.instance(oss.getService());
            storage.delete(oss.getUrl());
        }
        Integer res = ossRepository.deleteBatchIds(ids);
        return res > 0;
    }
}
