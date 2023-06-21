package com.rc.cloud.app.product.distributor.service.reputation;

import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationPageReqVO;
import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationUpdateReqVO;
import com.rc.cloud.app.product.distributor.convert.reputation.DistributorReputationConvert;
import com.rc.cloud.app.product.distributor.dal.dataobject.reputation.DistributorReputationDO;
import com.rc.cloud.app.product.distributor.dal.mysql.reputation.DistributorReputationMapper;
import com.rc.cloud.common.core.exception.ErrorCode;
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
    public Integer createReputation(AppDistributorReputationCreateReqVO createReqVO) {
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
    public void deleteReputation(Integer id) {
        // 校验存在
        validateReputationExists(id);
        // 删除
        reputationMapper.deleteById(id);
    }

    private void validateReputationExists(Integer id) {
        if (reputationMapper.selectById(id) == null) {
            throw exception(new ErrorCode(4,"REPUTATION_NOT_EXISTS"));
        }
    }

    @Override
    public DistributorReputationDO getReputation(Integer id) {
        return reputationMapper.selectById(id);
    }

    @Override
    public List<DistributorReputationDO> getReputationList(Collection<Integer> ids) {
        return reputationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorReputationDO> getReputationPage(AppDistributorReputationPageReqVO pageReqVO) {
        return reputationMapper.selectPage(pageReqVO);
    }


}
