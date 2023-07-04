package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorLevelMapper;
import com.rc.cloud.app.distributor.appearance.req.DistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorLevelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorLevelUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorLevelConvert;
import com.rc.cloud.app.distributor.application.service.DistributorLevelService;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 经销商客户等级 Service 实现类
 *
 * @author wjf
 */
@Service
@Validated
public class DistributorLevelServiceImpl implements DistributorLevelService {

    @Resource
    private DistributorLevelMapper levelMapper;

    @Override
    public Long createLevel(DistributorLevelCreateReqVO createReqVO) {
        // 插入
        DistributorLevelPO level = DistributorLevelConvert.INSTANCE.convert(createReqVO);
        levelMapper.insert(level);
        // 返回
        return level.getId();
    }

    @Override
    public void updateLevel(DistributorLevelUpdateReqVO updateReqVO) {
        // 校验存在
        validateLevelExists(updateReqVO.getId());
        // 更新
        DistributorLevelPO updateObj = DistributorLevelConvert.INSTANCE.convert(updateReqVO);
        levelMapper.updateById(updateObj);
    }

    @Override
    public void deleteLevel(Long id) {
        // 校验存在
        validateLevelExists(id);
        // 删除
        levelMapper.deleteById(id);
    }

    private void validateLevelExists(Long id) {
        if (levelMapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_LEVEL_NOT_EXISTS);
        }
    }

    @Override
    public DistributorLevelPO getLevel(Long id) {
        return levelMapper.selectById(id);
    }

    @Override
    public List<DistributorLevelPO> getLevelList(Collection<Long> ids) {
        return levelMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorLevelPO> getLevelPage(DistributorLevelPageReqVO pageReqVO) {
        return levelMapper.selectPage(pageReqVO);
    }

}
