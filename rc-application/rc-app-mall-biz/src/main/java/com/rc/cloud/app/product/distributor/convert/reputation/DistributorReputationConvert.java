package com.rc.cloud.app.product.distributor.convert.reputation;

import java.util.*;


import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationRespVO;
import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationUpdateReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.reputation.DistributorReputationDO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 经销商客户信誉 Convert
 *
 * @author wjf
 */
@Mapper
public interface DistributorReputationConvert {

    DistributorReputationConvert INSTANCE = Mappers.getMapper(DistributorReputationConvert.class);

    DistributorReputationDO convert(AppDistributorReputationCreateReqVO bean);

    DistributorReputationDO convert(AppDistributorReputationUpdateReqVO bean);

    AppDistributorReputationRespVO convert(DistributorReputationDO bean);

    List<AppDistributorReputationRespVO> convertList(List<DistributorReputationDO> list);

    PageResult<AppDistributorReputationRespVO> convertPage(PageResult<DistributorReputationDO> page);

}
