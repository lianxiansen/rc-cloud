package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationPO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorReputationPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 经销商客户信誉 Mapper
 *
 * @author wjf
 */
@Mapper
public interface DistributorReputationMapper extends BaseMapperX<DistributorReputationPO> {

    /**
     * 获取经销商信誉分页
     *
     * @param reqVO
     * @return DistributorReputationPO分页
     */
    default PageResult<DistributorReputationPO> selectPage(DistributorReputationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorReputationPO>()
                .likeIfPresent(DistributorReputationPO::getName, reqVO.getName())
                .eqIfPresent(DistributorReputationPO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorReputationPO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DistributorReputationPO::getId));
    }


}
