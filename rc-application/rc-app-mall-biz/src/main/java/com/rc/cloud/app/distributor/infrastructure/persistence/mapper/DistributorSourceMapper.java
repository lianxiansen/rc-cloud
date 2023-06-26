package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorSourceDO;
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
public interface DistributorSourceMapper extends BaseMapperX<DistributorSourceDO> {

    default PageResult<DistributorSourceDO> selectPage(AppDistributorSourcePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorSourceDO>()
                .likeIfPresent(DistributorSourceDO::getName, reqVO.getName())
                .eqIfPresent(DistributorSourceDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(DistributorSourceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DistributorSourceDO::getId));
    }
}
