package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelPO;
import com.rc.cloud.app.distributor.appearance.req.DistributorLevelPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 经销商客户等级 Mapper
 *
 * @author wjf
 */
@Mapper
public interface DistributorLevelMapper extends BaseMapperX<DistributorLevelPO> {

    default PageResult<DistributorLevelPO> selectPage(DistributorLevelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorLevelPO>()
                .likeIfPresent(DistributorLevelPO::getName, reqVO.getName())
                .eqIfPresent(DistributorLevelPO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorLevelPO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DistributorLevelPO::getId));
    }

}
