package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 经销商渠道 Mapper
 *
 * @author wjf
 */
@Mapper
public interface DistributorChannelMapper extends BaseMapperX<DistributorChannelDO> {

    default PageResult<DistributorChannelDO> selectPage(AppDistributorChannelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorChannelDO>()
                .likeIfPresent(DistributorChannelDO::getName, reqVO.getName())
                .eqIfPresent(DistributorChannelDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorChannelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DistributorChannelDO::getId));
    }
}
