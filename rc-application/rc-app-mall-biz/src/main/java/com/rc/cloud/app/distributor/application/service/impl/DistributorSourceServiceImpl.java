package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorSourceConvert;
import com.rc.cloud.app.distributor.application.service.DistributorSourceService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorSourcePO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorSourceMapper;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 经销商来源 Service 实现类
 *
 * @author wjf
 */
@Service
@Validated
public class DistributorSourceServiceImpl implements DistributorSourceService {

    @Resource
    private DistributorSourceMapper sourceMapper;

    @Override
    public Long createSource(AppDistributorSourceCreateReqVO createReqVO) {
        // 插入
        DistributorSourcePO source = DistributorSourceConvert.INSTANCE.convert(createReqVO);
        sourceMapper.insert(source);
        // 返回
        return source.getId();
    }

    @Override
    public void updateSource(AppDistributorSourceUpdateReqVO updateReqVO) {
        // 校验存在
        validateSourceExists(updateReqVO.getId());
        // 更新
        DistributorSourcePO updateObj = DistributorSourceConvert.INSTANCE.convert(updateReqVO);
        sourceMapper.updateById(updateObj);
    }

    @Override
    public void deleteSource(Long id) {
        // 校验存在
        validateSourceExists(id);
        // 删除
        sourceMapper.deleteById(id);
    }

    private void validateSourceExists(Long id) {
        if (sourceMapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_SOURCE_EXISTS);
        }
    }

    @Override
    public DistributorSourcePO getSource(Long id) {
        return sourceMapper.selectById(id);
    }

    @Override
    public List<DistributorSourcePO> getSourceList(Collection<Long> ids) {
        return sourceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorSourcePO> getSourcePage(AppDistributorSourcePageReqVO pageReqVO) {
        return sourceMapper.selectPage(pageReqVO);
    }

}
