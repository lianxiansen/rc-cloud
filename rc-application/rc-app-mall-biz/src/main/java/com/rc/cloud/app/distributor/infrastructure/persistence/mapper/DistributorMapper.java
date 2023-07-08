package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import java.util.*;

import com.rc.cloud.app.distributor.appearance.req.DistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorPageReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 经销商 Mapper
 *
 * @author wjf
 */
@Mapper
public interface DistributorMapper extends BaseMapperX<DistributorPO> {

    default PageResult<DistributorPO> selectPage(DistributorPageReqVO reqVO) {

        // MyBatis Plus 关联表 查询
//        IPage<DistributorPO> mpPage = MyBatisUtils.buildPage(reqVO);
//        IPage<DistributorPO> page = selectJoinPage(mpPage, DistributorPO.class,
//                ((MPJLambdaWrapperX<DistributorPO>) new MPJLambdaWrapperX<DistributorPO>()
//                        .selectAll(DistributorPO.class)
//                        .leftJoin(distributorContactPO.class, distributorContactPO::getDistributorId, DistributorPO::getId))
//                        .likeIfPresent(distributorContactPO::getMobile, reqVO.getMobile())
//                        .likeIfPresent(distributorContactPO::getName, reqVO.getContact())
//                        .likeIfPresent(DistributorPO::getCompanyName, reqVO.getCompanyName())
//                        .likeIfPresent(DistributorPO::getProvince, reqVO.getProvince())
//                        .eqIfPresent(DistributorPO::getCity, reqVO.getCity())
//                        .eqIfPresent(DistributorPO::getCounty, reqVO.getCounty())
//                        .eqIfPresent(DistributorPO::getAddress, reqVO.getAddress())
//                        .eqIfPresent(DistributorPO::getStartTime, reqVO.getStartTime())
//                        .eqIfPresent(DistributorPO::getEndTime, reqVO.getEndTime())
//                        .eqIfPresent(DistributorPO::getStatus, reqVO.getStatus())
//                        .eqIfPresent(DistributorPO::getRemarks, reqVO.getRemarks())
//                        .eqIfPresent(DistributorPO::getCreator, reqVO.getCreator())
//                        .eqIfPresent(DistributorPO::getAdminId, reqVO.getAdminId())
//                        .eqIfPresent(DistributorPO::getTelephone, reqVO.getTelephone())
//                        .eqIfPresent(DistributorPO::getChannel, reqVO.getChannel())
//                        .eqIfPresent(DistributorPO::getSource, reqVO.getSource())
//                        .eqIfPresent(DistributorPO::getLevel, reqVO.getLevel())
//                        .eqIfPresent(DistributorPO::getReputation, reqVO.getReputation())
//                        .eqIfPresent(DistributorPO::getEstablishedTime, reqVO.getEstablishedTime())
//                        .eqIfPresent(DistributorPO::getDeleted, reqVO.getDeleted())
//                        .eqIfPresent(DistributorPO::getLocking, reqVO.getLocking())
//                        .orderByDesc(DistributorPO::getId)
//        );
//        return new PageResult<>(page.getRecords(), page.getTotal());

        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorPO>()
                .likeIfPresent(DistributorPO::getCompanyName, reqVO.getCompanyName())
                .likeIfPresent(DistributorPO::getContact, reqVO.getContact())
                .likeIfPresent(DistributorPO::getMobile, reqVO.getMobile())
                .eqIfPresent(DistributorPO::getProvince, reqVO.getProvince())
                .eqIfPresent(DistributorPO::getCity, reqVO.getCity())
                .eqIfPresent(DistributorPO::getCounty, reqVO.getCounty())
                .eqIfPresent(DistributorPO::getAddress, reqVO.getAddress())
                .eqIfPresent(DistributorPO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(DistributorPO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(DistributorPO::getStatus, reqVO.getStatus())
                .eqIfPresent(DistributorPO::getRemarks, reqVO.getRemarks())
                .betweenIfPresent(DistributorPO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DistributorPO::getCreator, reqVO.getCreator())
                .eqIfPresent(DistributorPO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(DistributorPO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(DistributorPO::getChannel, reqVO.getChannel())
                .eqIfPresent(DistributorPO::getSource, reqVO.getSource())
                .eqIfPresent(DistributorPO::getLevel, reqVO.getLevel())
                .eqIfPresent(DistributorPO::getReputation, reqVO.getReputation())
                .eqIfPresent(DistributorPO::getEstablishedTime, reqVO.getEstablishedTime())
                .eqIfPresent(DistributorPO::getDeleted, reqVO.getDeleted())
                .eqIfPresent(DistributorPO::getLocking, reqVO.getLocking())
                .orderByDesc(DistributorPO::getCreateTime));
    }

    default List<DistributorPO> selectList(DistributorExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DistributorPO>()
                .likeIfPresent(DistributorPO::getCompanyName, reqVO.getCompanyName())
                .likeIfPresent(DistributorPO::getMobile, reqVO.getMobile())
                .likeIfPresent(DistributorPO::getContact, reqVO.getContact())
                .eqIfPresent(DistributorPO::getProvince, reqVO.getProvince())
                .eqIfPresent(DistributorPO::getCity, reqVO.getCity())
                .eqIfPresent(DistributorPO::getCounty, reqVO.getCounty())
                .eqIfPresent(DistributorPO::getAddress, reqVO.getAddress())
                .eqIfPresent(DistributorPO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(DistributorPO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(DistributorPO::getStatus, reqVO.getStatus())
                .eqIfPresent(DistributorPO::getRemarks, reqVO.getRemarks())
                .betweenIfPresent(DistributorPO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DistributorPO::getCreator, reqVO.getCreator())
                .eqIfPresent(DistributorPO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(DistributorPO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(DistributorPO::getChannel, reqVO.getChannel())
                .eqIfPresent(DistributorPO::getSource, reqVO.getSource())
                .eqIfPresent(DistributorPO::getLevel, reqVO.getLevel())
                .eqIfPresent(DistributorPO::getReputation, reqVO.getReputation())
                .eqIfPresent(DistributorPO::getEstablishedTime, reqVO.getEstablishedTime())
                .eqIfPresent(DistributorPO::getDeleted, reqVO.getDeleted())
                .eqIfPresent(DistributorPO::getLocking, reqVO.getLocking())
                .orderByDesc(DistributorPO::getId));
    }

}
