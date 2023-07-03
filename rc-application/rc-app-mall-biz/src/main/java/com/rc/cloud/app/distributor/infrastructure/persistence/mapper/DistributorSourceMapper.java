package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorSourcePO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 经销商来源 Mapper
 *
 * @author wjf
 */
@Mapper
public interface DistributorSourceMapper extends BaseMapperX<DistributorSourcePO> {

    default PageResult<DistributorSourcePO> selectPage(AppDistributorSourcePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorSourcePO>()
                .likeIfPresent(DistributorSourcePO::getName, reqVO.getName())
                .eqIfPresent(DistributorSourcePO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorSourcePO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DistributorSourcePO::getId));
    }
}
