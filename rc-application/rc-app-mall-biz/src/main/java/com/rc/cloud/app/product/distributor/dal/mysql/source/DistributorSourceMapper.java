package com.rc.cloud.app.product.distributor.dal.mysql.source;

import java.util.*;

import com.rc.cloud.app.product.distributor.controller.app.source.vo.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.source.DistributorSourceDO;
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
                .eqIfPresent(DistributorSourceDO::getExplain, reqVO.getExplain())
                .betweenIfPresent(DistributorSourceDO::getCreatetime, reqVO.getCreatetime())
                .orderByDesc(DistributorSourceDO::getId));
    }
}
