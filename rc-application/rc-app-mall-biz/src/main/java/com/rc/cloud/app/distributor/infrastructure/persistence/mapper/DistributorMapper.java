package com.rc.cloud.app.distributor.infrastructure.persistence.mapper;

import java.util.*;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorPageReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDO;
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
public interface DistributorMapper extends BaseMapperX<DistributorDO> {

    default PageResult<DistributorDO> selectPage(AppDistributorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DistributorDO>()
                .likeIfPresent(DistributorDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(DistributorDO::getContacts, reqVO.getContacts())
                .eqIfPresent(DistributorDO::getMobile, reqVO.getMobile())
                .eqIfPresent(DistributorDO::getPassword, reqVO.getPassword())
                .eqIfPresent(DistributorDO::getProvince, reqVO.getProvince())
                .eqIfPresent(DistributorDO::getCity, reqVO.getCity())
                .eqIfPresent(DistributorDO::getCounty, reqVO.getCounty())
                .eqIfPresent(DistributorDO::getAddress, reqVO.getAddress())
                .eqIfPresent(DistributorDO::getStart, reqVO.getStart())
                .eqIfPresent(DistributorDO::getEnd, reqVO.getEnd())
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
                .eqIfPresent(DistributorDO::getZtAddress, reqVO.getZtAddress())
                .eqIfPresent(DistributorDO::getZtMianji, reqVO.getZtMianji())
                .eqIfPresent(DistributorDO::getZtImage, reqVO.getZtImage())
                .eqIfPresent(DistributorDO::getMdAddress, reqVO.getMdAddress())
                .eqIfPresent(DistributorDO::getMdMianji, reqVO.getMdMianji())
                .eqIfPresent(DistributorDO::getMdImage, reqVO.getMdImage())
                .eqIfPresent(DistributorDO::getCkAddress, reqVO.getCkAddress())
                .eqIfPresent(DistributorDO::getCkMianji, reqVO.getCkMianji())
                .eqIfPresent(DistributorDO::getCkImage, reqVO.getCkImage())
                .eqIfPresent(DistributorDO::getDeleted, reqVO.getDeleted())
                .eqIfPresent(DistributorDO::getLocking, reqVO.getLocking())
                .orderByDesc(DistributorDO::getId));
    }

    default List<DistributorDO> selectList(AppDistributorExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DistributorDO>()
                .likeIfPresent(DistributorDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(DistributorDO::getContacts, reqVO.getContacts())
                .eqIfPresent(DistributorDO::getMobile, reqVO.getMobile())
                .eqIfPresent(DistributorDO::getPassword, reqVO.getPassword())
                .eqIfPresent(DistributorDO::getProvince, reqVO.getProvince())
                .eqIfPresent(DistributorDO::getCity, reqVO.getCity())
                .eqIfPresent(DistributorDO::getCounty, reqVO.getCounty())
                .eqIfPresent(DistributorDO::getAddress, reqVO.getAddress())
                .eqIfPresent(DistributorDO::getStart, reqVO.getStart())
                .eqIfPresent(DistributorDO::getEnd, reqVO.getEnd())
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
                .eqIfPresent(DistributorDO::getZtAddress, reqVO.getZtAddress())
                .eqIfPresent(DistributorDO::getZtMianji, reqVO.getZtMianji())
                .eqIfPresent(DistributorDO::getZtImage, reqVO.getZtImage())
                .eqIfPresent(DistributorDO::getMdAddress, reqVO.getMdAddress())
                .eqIfPresent(DistributorDO::getMdMianji, reqVO.getMdMianji())
                .eqIfPresent(DistributorDO::getMdImage, reqVO.getMdImage())
                .eqIfPresent(DistributorDO::getCkAddress, reqVO.getCkAddress())
                .eqIfPresent(DistributorDO::getCkMianji, reqVO.getCkMianji())
                .eqIfPresent(DistributorDO::getCkImage, reqVO.getCkImage())
                .eqIfPresent(DistributorDO::getDeleted, reqVO.getDeleted())
                .eqIfPresent(DistributorDO::getLocking, reqVO.getLocking())
                .orderByDesc(DistributorDO::getId));
    }

}
