package com.rc.cloud.app.product.distributor.dal.mysql.channel;

import java.util.*;

import com.rc.cloud.app.product.distributor.controller.app.channel.vo.AppDistributorChannelPageReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.channel.DistributorChannelDO;
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
                .eqIfPresent(DistributorChannelDO::getExplain, reqVO.getExplain())
                .betweenIfPresent(DistributorChannelDO::getCreatetime, reqVO.getCreatetime())
                .orderByDesc(DistributorChannelDO::getId));
    }
}
