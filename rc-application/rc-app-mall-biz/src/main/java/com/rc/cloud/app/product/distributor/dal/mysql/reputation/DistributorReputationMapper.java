package com.rc.cloud.app.product.distributor.dal.mysql.reputation;

import java.util.*;

import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationPageReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.reputation.DistributorReputationDO;
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
                .eqIfPresent(DistributorReputationDO::getExplain, reqVO.getExplain())
                .betweenIfPresent(DistributorReputationDO::getCreatetime, reqVO.getCreatetime())
                .orderByDesc(DistributorReputationDO::getId));
    }


}
