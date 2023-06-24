package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelDO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelPageReqVO;
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
public interface DistributorLevelMapper extends BaseMapperX<DistributorLevelDO> {

    default PageResult<DistributorLevelDO> selectPage(AppDistributorLevelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorLevelDO>()
                .likeIfPresent(DistributorLevelDO::getName, reqVO.getName())
                .eqIfPresent(DistributorLevelDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorLevelDO::getCreatetime, reqVO.getCreatetime())
                .orderByDesc(DistributorLevelDO::getId));
    }

}
