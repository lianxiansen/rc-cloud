package com.rc.cloud.app.product.distributor.dal.mysql.distributor;

import java.util.*;

import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorExportReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorPageReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.distributor.DistributorDO;
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
                .betweenIfPresent(DistributorDO::getCreatetime, reqVO.getCreatetime())
                .eqIfPresent(DistributorDO::getBy, reqVO.getBy())
                .eqIfPresent(DistributorDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(DistributorDO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(DistributorDO::getChannel, reqVO.getChannel())
                .eqIfPresent(DistributorDO::getCustomers, reqVO.getCustomers())
                .eqIfPresent(DistributorDO::getLevel, reqVO.getLevel())
                .eqIfPresent(DistributorDO::getReputation, reqVO.getReputation())
                .eqIfPresent(DistributorDO::getChengli, reqVO.getChengli())
                .eqIfPresent(DistributorDO::getZtAddress, reqVO.getZtAddress())
                .eqIfPresent(DistributorDO::getZtMianji, reqVO.getZtMianji())
                .eqIfPresent(DistributorDO::getZtImage, reqVO.getZtImage())
                .eqIfPresent(DistributorDO::getMdAddress, reqVO.getMdAddress())
                .eqIfPresent(DistributorDO::getMdMianji, reqVO.getMdMianji())
                .eqIfPresent(DistributorDO::getMdImage, reqVO.getMdImage())
                .eqIfPresent(DistributorDO::getCkAddress, reqVO.getCkAddress())
                .eqIfPresent(DistributorDO::getCkMianji, reqVO.getCkMianji())
                .eqIfPresent(DistributorDO::getCkImage, reqVO.getCkImage())
                .eqIfPresent(DistributorDO::getIsdelete, reqVO.getIsdelete())
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
                .betweenIfPresent(DistributorDO::getCreatetime, reqVO.getCreatetime())
                .eqIfPresent(DistributorDO::getBy, reqVO.getBy())
                .eqIfPresent(DistributorDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(DistributorDO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(DistributorDO::getChannel, reqVO.getChannel())
                .eqIfPresent(DistributorDO::getCustomers, reqVO.getCustomers())
                .eqIfPresent(DistributorDO::getLevel, reqVO.getLevel())
                .eqIfPresent(DistributorDO::getReputation, reqVO.getReputation())
                .eqIfPresent(DistributorDO::getChengli, reqVO.getChengli())
                .eqIfPresent(DistributorDO::getZtAddress, reqVO.getZtAddress())
                .eqIfPresent(DistributorDO::getZtMianji, reqVO.getZtMianji())
                .eqIfPresent(DistributorDO::getZtImage, reqVO.getZtImage())
                .eqIfPresent(DistributorDO::getMdAddress, reqVO.getMdAddress())
                .eqIfPresent(DistributorDO::getMdMianji, reqVO.getMdMianji())
                .eqIfPresent(DistributorDO::getMdImage, reqVO.getMdImage())
                .eqIfPresent(DistributorDO::getCkAddress, reqVO.getCkAddress())
                .eqIfPresent(DistributorDO::getCkMianji, reqVO.getCkMianji())
                .eqIfPresent(DistributorDO::getCkImage, reqVO.getCkImage())
                .eqIfPresent(DistributorDO::getIsdelete, reqVO.getIsdelete())
                .eqIfPresent(DistributorDO::getLocking, reqVO.getLocking())
                .orderByDesc(DistributorDO::getId));
    }

}
