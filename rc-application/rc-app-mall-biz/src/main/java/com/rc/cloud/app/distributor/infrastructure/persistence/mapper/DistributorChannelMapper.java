package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.appearance.req.DistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelPO;
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
public interface DistributorChannelMapper extends BaseMapperX<DistributorChannelPO> {

    default PageResult<DistributorChannelPO> selectPage(DistributorChannelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorChannelPO>()
                .likeIfPresent(DistributorChannelPO::getName, reqVO.getName())
                .eqIfPresent(DistributorChannelPO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorChannelPO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DistributorChannelPO::getId));
    }
}
