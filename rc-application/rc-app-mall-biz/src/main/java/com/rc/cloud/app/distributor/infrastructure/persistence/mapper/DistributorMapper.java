package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import java.util.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorPageReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.mybatis.core.query.MPJLambdaWrapperX;
import com.rc.cloud.common.mybatis.core.util.MyBatisUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 经销商 Mapper
 *
 * @author wjf
 */
@Mapper
public interface DistributorMapper extends BaseMapperX<DistributorDO> {

    default PageResult<DistributorDO> selectPage(AppDistributorPageReqVO reqVO) {

        // MyBatis Plus 关联表 查询
//        IPage<DistributorDO> mpPage = MyBatisUtils.buildPage(reqVO);
//        IPage<DistributorDO> page = selectJoinPage(mpPage, DistributorDO.class,
//                ((MPJLambdaWrapperX<DistributorDO>) new MPJLambdaWrapperX<DistributorDO>()
//                        .selectAll(DistributorDO.class)
//                        .leftJoin(DistributorContactDO.class, DistributorContactDO::getDistributorId, DistributorDO::getId))
//                        .likeIfPresent(DistributorContactDO::getMobile, reqVO.getMobile())
//                        .likeIfPresent(DistributorContactDO::getName, reqVO.getContact())
//                        .likeIfPresent(DistributorDO::getCompanyName, reqVO.getCompanyName())
//                        .likeIfPresent(DistributorDO::getProvince, reqVO.getProvince())
//                        .eqIfPresent(DistributorDO::getCity, reqVO.getCity())
//                        .eqIfPresent(DistributorDO::getCounty, reqVO.getCounty())
//                        .eqIfPresent(DistributorDO::getAddress, reqVO.getAddress())
//                        .eqIfPresent(DistributorDO::getStartTime, reqVO.getStartTime())
//                        .eqIfPresent(DistributorDO::getEndTime, reqVO.getEndTime())
//                        .eqIfPresent(DistributorDO::getStatus, reqVO.getStatus())
//                        .eqIfPresent(DistributorDO::getRemarks, reqVO.getRemarks())
//                        .eqIfPresent(DistributorDO::getCreator, reqVO.getCreator())
//                        .eqIfPresent(DistributorDO::getAdminId, reqVO.getAdminId())
//                        .eqIfPresent(DistributorDO::getTelephone, reqVO.getTelephone())
//                        .eqIfPresent(DistributorDO::getChannel, reqVO.getChannel())
//                        .eqIfPresent(DistributorDO::getSource, reqVO.getSource())
//                        .eqIfPresent(DistributorDO::getLevel, reqVO.getLevel())
//                        .eqIfPresent(DistributorDO::getReputation, reqVO.getReputation())
//                        .eqIfPresent(DistributorDO::getEstablishedTime, reqVO.getEstablishedTime())
//                        .eqIfPresent(DistributorDO::getDeleted, reqVO.getDeleted())
//                        .eqIfPresent(DistributorDO::getLocking, reqVO.getLocking())
//                        .orderByDesc(DistributorDO::getId)
//        );
//        return new PageResult<>(page.getRecords(), page.getTotal());

        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorDO>()
                .likeIfPresent(DistributorDO::getCompanyName, reqVO.getCompanyName())
                .likeIfPresent(DistributorDO::getContact, reqVO.getContact())
                .likeIfPresent(DistributorDO::getMobile, reqVO.getMobile())
                .eqIfPresent(DistributorDO::getProvince, reqVO.getProvince())
                .eqIfPresent(DistributorDO::getCity, reqVO.getCity())
                .eqIfPresent(DistributorDO::getCounty, reqVO.getCounty())
                .eqIfPresent(DistributorDO::getAddress, reqVO.getAddress())
                .eqIfPresent(DistributorDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(DistributorDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(DistributorDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DistributorDO::getRemarks, reqVO.getRemarks())
                .betweenIfPresent(DistributorDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DistributorDO::getCreator, reqVO.getCreator())
                .eqIfPresent(DistributorDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(DistributorDO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(DistributorDO::getChannel, reqVO.getChannel())
                .eqIfPresent(DistributorDO::getSource, reqVO.getSource())
                .eqIfPresent(DistributorDO::getLevel, reqVO.getLevel())
                .eqIfPresent(DistributorDO::getReputation, reqVO.getReputation())
                .eqIfPresent(DistributorDO::getEstablishedTime, reqVO.getEstablishedTime())
                .eqIfPresent(DistributorDO::getDeleted, reqVO.getDeleted())
                .eqIfPresent(DistributorDO::getLocking, reqVO.getLocking())
                .orderByDesc(DistributorDO::getId));
    }

    default List<DistributorDO> selectList(AppDistributorExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DistributorDO>()
                .likeIfPresent(DistributorDO::getCompanyName, reqVO.getCompanyName())
                .likeIfPresent(DistributorDO::getMobile, reqVO.getMobile())
                .likeIfPresent(DistributorDO::getContact, reqVO.getContact())
                .eqIfPresent(DistributorDO::getProvince, reqVO.getProvince())
                .eqIfPresent(DistributorDO::getCity, reqVO.getCity())
                .eqIfPresent(DistributorDO::getCounty, reqVO.getCounty())
                .eqIfPresent(DistributorDO::getAddress, reqVO.getAddress())
                .eqIfPresent(DistributorDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(DistributorDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(DistributorDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DistributorDO::getRemarks, reqVO.getRemarks())
                .betweenIfPresent(DistributorDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DistributorDO::getCreator, reqVO.getCreator())
                .eqIfPresent(DistributorDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(DistributorDO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(DistributorDO::getChannel, reqVO.getChannel())
                .eqIfPresent(DistributorDO::getSource, reqVO.getSource())
                .eqIfPresent(DistributorDO::getLevel, reqVO.getLevel())
                .eqIfPresent(DistributorDO::getReputation, reqVO.getReputation())
                .eqIfPresent(DistributorDO::getEstablishedTime, reqVO.getEstablishedTime())
                .eqIfPresent(DistributorDO::getDeleted, reqVO.getDeleted())
                .eqIfPresent(DistributorDO::getLocking, reqVO.getLocking())
                .orderByDesc(DistributorDO::getId));
    }

}
