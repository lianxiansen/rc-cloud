package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationDO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationPageReqVO;
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
public interface DistributorReputationMapper extends BaseMapperX<DistributorReputationDO> {

    default PageResult<DistributorReputationDO> selectPage(AppDistributorReputationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorReputationDO>()
                .likeIfPresent(DistributorReputationDO::getName, reqVO.getName())
                .eqIfPresent(DistributorReputationDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorReputationDO::getCreatetime, reqVO.getCreatetime())
                .orderByDesc(DistributorReputationDO::getId));
    }


}
