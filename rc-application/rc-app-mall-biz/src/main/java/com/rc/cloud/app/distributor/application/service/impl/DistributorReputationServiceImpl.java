package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.DistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorReputationConvert;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorReputationMapper;
import com.rc.cloud.app.distributor.application.service.DistributorReputationService;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 经销商客户信誉 Service 实现类
 *
 * @author wjf
 */
@Service
@Validated
public class DistributorReputationServiceImpl implements DistributorReputationService {

    @Resource
    private DistributorReputationMapper reputationMapper;

    @Override
    public Long createReputation(DistributorReputationCreateReqVO createReqVO) {
        // 插入
        DistributorReputationPO reputation = DistributorReputationConvert.INSTANCE.convert(createReqVO);
        reputationMapper.insert(reputation);
        // 返回
        return reputation.getId();
    }

    @Override
    public void updateReputation(DistributorReputationUpdateReqVO updateReqVO) {
        // 校验存在
        validateReputationExists(updateReqVO.getId());
        // 更新
        DistributorReputationPO updateObj = DistributorReputationConvert.INSTANCE.convert(updateReqVO);
        reputationMapper.updateById(updateObj);
    }

    @Override
    public void deleteReputation(Long id) {
        // 校验存在
        validateReputationExists(id);
        // 删除
        reputationMapper.deleteById(id);
    }

    private void validateReputationExists(Long id) {
        if (reputationMapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_REPUTATION_NOT_EXISTS);
        }
    }

    @Override
    public DistributorReputationPO getReputation(Long id) {
        return reputationMapper.selectById(id);
    }

    @Override
    public List<DistributorReputationPO> getAll() {
        return reputationMapper.selectList();
    }

    @Override
    public List<DistributorReputationPO> getReputationList(Collection<Long> ids) {
        return reputationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorReputationPO> getReputationPage(DistributorReputationPageReqVO pageReqVO) {
        return reputationMapper.selectPage(pageReqVO);
    }


}
