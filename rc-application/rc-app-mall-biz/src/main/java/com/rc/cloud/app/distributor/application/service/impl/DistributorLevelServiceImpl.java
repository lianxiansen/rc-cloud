package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorLevelMapper;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorLevelConvert;
import com.rc.cloud.app.distributor.application.service.DistributorLevelService;
import com.rc.cloud.common.core.exception.ErrorCode;
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
    public Integer createLevel(AppDistributorLevelCreateReqVO createReqVO) {
        // 插入
        DistributorLevelDO level = DistributorLevelConvert.INSTANCE.convert(createReqVO);
        levelMapper.insert(level);
        // 返回
        return level.getId();
    }

    @Override
    public void updateLevel(AppDistributorLevelUpdateReqVO updateReqVO) {
        // 校验存在
        validateLevelExists(updateReqVO.getId());
        // 更新
        DistributorLevelDO updateObj = DistributorLevelConvert.INSTANCE.convert(updateReqVO);
        levelMapper.updateById(updateObj);
    }

    @Override
    public void deleteLevel(Integer id) {
        // 校验存在
        validateLevelExists(id);
        // 删除
        levelMapper.deleteById(id);
    }

    private void validateLevelExists(Integer id) {
        if (levelMapper.selectById(id) == null) {
            throw exception(new ErrorCode(3,"LEVEL_NOT_EXISTS"));
        }
    }

    @Override
    public DistributorLevelDO getLevel(Integer id) {
        return levelMapper.selectById(id);
    }

    @Override
    public List<DistributorLevelDO> getLevelList(Collection<Integer> ids) {
        return levelMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorLevelDO> getLevelPage(AppDistributorLevelPageReqVO pageReqVO) {
        return levelMapper.selectPage(pageReqVO);
    }

}