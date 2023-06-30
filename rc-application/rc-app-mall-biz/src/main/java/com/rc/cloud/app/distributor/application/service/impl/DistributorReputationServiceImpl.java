package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorReputationConvert;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorReputationMapper;
import com.rc.cloud.app.distributor.application.service.DistributorReputationService;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
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
    public Long createReputation(AppDistributorReputationCreateReqVO createReqVO) {
        // 插入
        DistributorReputationDO reputation = DistributorReputationConvert.INSTANCE.convert(createReqVO);
        reputationMapper.insert(reputation);
        // 返回
        return reputation.getId();
    }

    @Override
    public void updateReputation(AppDistributorReputationUpdateReqVO updateReqVO) {
        // 校验存在
        validateReputationExists(updateReqVO.getId());
        // 更新
        DistributorReputationDO updateObj = DistributorReputationConvert.INSTANCE.convert(updateReqVO);
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
    public DistributorReputationDO getReputation(Long id) {
        return reputationMapper.selectById(id);
    }

    @Override
    public List<DistributorReputationDO> getReputationList(Collection<Long> ids) {
        return reputationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorReputationDO> getReputationPage(AppDistributorReputationPageReqVO pageReqVO) {
        return reputationMapper.selectPage(pageReqVO);
    }


}
